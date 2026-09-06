# Bài giảng DSA: Stack & Monotonic Stack Patterns

Stack là một trong những cấu trúc dữ liệu xuất hiện rất nhiều trong coding interview. Điều quan trọng không phải chỉ biết `push/pop`, mà là nhận ra:

> **Khi nào bài toán đang ngầm yêu cầu Stack?**
> **Khi nào Stack thông thường chưa đủ và cần Monotonic Stack?**

Có thể chia các bài Stack thành hai nhóm lớn:

```text
STACK
│
├── 1. Normal Stack
│   ├── Matching / Parentheses
│   ├── Simulation
│   ├── Expression Evaluation
│   ├── String Reduction / Cancellation
│   ├── Nested Structure
│   └── Stack with extra state
│
└── 2. Monotonic Stack
    ├── Next Greater Element
    ├── Previous Greater Element
    ├── Next Smaller Element
    ├── Previous Smaller Element
    ├── Histogram / Boundary
    ├── Contribution Technique
    └── Greedy Monotonic Stack
```

---

# Phần I — Stack cơ bản

## 1. Stack là gì?

Stack hoạt động theo nguyên tắc:

> **LIFO — Last In, First Out**

Phần tử được thêm vào cuối cùng sẽ được lấy ra đầu tiên.

Ví dụ:

```text
push(10)
push(20)
push(30)

Stack:

TOP
 ↓
30
20
10
```

Nếu:

```text
pop()
```

thì lấy ra `30`.

---

## 2. Stack trong Java

Trong Java interview, nên ưu tiên:

```java
Deque<Integer> stack = new ArrayDeque<>();
```

thay vì:

```java
Stack<Integer> stack = new Stack<>();
```

Template:

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);
stack.push(20);

int top = stack.peek();

int removed = stack.pop();

boolean empty = stack.isEmpty();
```

Hoặc:

```java
stack.addLast(x);
stack.removeLast();
stack.peekLast();
```

Nhưng trong DSA, syntax này thường dễ đọc hơn:

```java
push()
pop()
peek()
```

---

# Pattern 1 — Matching / Balanced Structure

Đây là pattern Stack cơ bản nhất.

Các dấu hiệu:

```text
()
[]
{}
<tag></tag>
begin/end
open/close
```

Ta gặp một ký hiệu mở → đưa vào stack.

Ta gặp ký hiệu đóng → kiểm tra phần tử gần nhất chưa được đóng.

---

# Ví dụ: LeetCode 20 — Valid Parentheses

Cho:

```text
s = "()[]{}"
```

kiểm tra chuỗi ngoặc có hợp lệ không.

Ví dụ:

```text
"([])"
```

Hợp lệ.

Nhưng:

```text
"([)]"
```

không hợp lệ.

---

## Tại sao cần Stack?

Với:

```text
(
  [
  ]
)
```

dấu đóng `]` phải match với dấu mở **gần nhất** là `[`.

Đây chính xác là:

```text
Last Opened
First Closed
```

hay LIFO.

---

## Algorithm

Nếu gặp opening bracket:

```text
(
[
{
```

thì push.

Nếu gặp closing bracket:

```text
)
]
}
```

thì:

```text
stack không được empty
top phải match
pop
```

Cuối cùng stack phải empty.

---

## Implementation

```java
class Solution {
    public boolean isValid(String s) {

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {

            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {

                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();

                if (c == ')' && top != '(') {
                    return false;
                }

                if (c == ']' && top != '[') {
                    return false;
                }

                if (c == '}' && top != '{') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
```

Complexity:

```text
Time:  O(n)
Space: O(n)
```

---

# Pattern nhận diện

Khi đề có:

> "matching pair"

hoặc:

> "nested"

hoặc:

> "most recent unmatched..."

thì nghĩ đến Stack.

Các bài:

```text
20. Valid Parentheses
32. Longest Valid Parentheses
921. Minimum Add to Make Parentheses Valid
1249. Minimum Remove to Make Valid Parentheses
856. Score of Parentheses
```

---

# Pattern 2 — Stack Simulation

Có những bài bản chất chỉ là mô phỏng một quá trình mà thao tác gần nhất cần được xử lý trước.

Ví dụ:

```text
Asteroid Collision
Baseball Game
Browser History
Undo
Path processing
```

---

# Ví dụ: LeetCode 735 — Asteroid Collision

Mỗi asteroid:

```text
positive → đi phải
negative → đi trái
```

Ví dụ:

```text
[5, 10, -5]
```

`10` và `-5` va chạm.

Kết quả:

```text
[5, 10]
```

---

## Insight

Một asteroid âm mới xuất hiện chỉ có thể va chạm với asteroid dương **gần nhất phía trước nó**.

Do đó:

```text
stack.top()
```

là asteroid cần xét đầu tiên.

---

## Implementation

```java
class Solution {
    public int[] asteroidCollision(int[] asteroids) {

        Deque<Integer> stack = new ArrayDeque<>();

        for (int asteroid : asteroids) {

            boolean alive = true;

            while (
                alive &&
                asteroid < 0 &&
                !stack.isEmpty() &&
                stack.peek() > 0
            ) {

                int top = stack.peek();

                if (top < -asteroid) {
                    stack.pop();
                } else if (top == -asteroid) {
                    stack.pop();
                    alive = false;
                } else {
                    alive = false;
                }
            }

            if (alive) {
                stack.push(asteroid);
            }
        }

        int[] result = new int[stack.size()];

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }
}
```

---

# Pattern 3 — Remove / Cancel Adjacent Elements

Một pattern Stack rất phổ biến:

```text
a b b a
```

Nếu hai phần tử cạnh nhau giống nhau → remove.

Sau khi remove:

```text
a a
```

lại tiếp tục remove.

Điểm quan trọng là:

> Sau khi xóa, những phần tử trước đó có thể trở thành adjacent.

Stack cực kỳ phù hợp.

---

# LeetCode 1047 — Remove All Adjacent Duplicates In String

Ví dụ:

```text
abbaca
```

Process:

```text
a
ab
abb → remove bb → a
aa → remove aa
c
ca
```

Result:

```text
ca
```

Implementation:

```java
class Solution {
    public String removeDuplicates(String s) {

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {

            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder result = new StringBuilder();

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.reverse().toString();
    }
}
```

---

# Pattern 4 — Expression Evaluation

Stack xuất hiện rất nhiều trong:

```text
calculator
postfix
prefix
infix
operator precedence
```

---

# LeetCode 150 — Evaluate Reverse Polish Notation

Ví dụ:

```text
["2", "1", "+", "3", "*"]
```

Tương đương:

```text
(2 + 1) * 3
```

Result:

```text
9
```

---

## Algorithm

Nếu là number:

```java
push(number)
```

Nếu là operator:

```text
b = pop()
a = pop()

result = a operator b
push(result)
```

Chú ý thứ tự:

```text
a - b
a / b
```

không phải:

```text
b - a
```

---

## Implementation

```java
class Solution {
    public int evalRPN(String[] tokens) {

        Deque<Integer> stack = new ArrayDeque<>();

        for (String token : tokens) {

            switch (token) {

                case "+": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a + b);
                    break;
                }

                case "-": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                    break;
                }

                case "*": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a * b);
                    break;
                }

                case "/": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a / b);
                    break;
                }

                default:
                    stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }
}
```

---

# Pattern 5 — Nested Structure

Một dấu hiệu khác:

```text
3[a2[c]]
```

Cấu trúc lồng nhau.

Ví dụ:

```text
3[a2[c]]
```

→

```text
accaccacc
```

Đây là LeetCode 394 — Decode String.

---

## Ý tưởng

Khi gặp:

```text
[
```

ta cần ghi nhớ context hiện tại:

```text
currentString
repeatCount
```

Sau đó bắt đầu xử lý một level mới.

Khi gặp:

```text
]
```

ta lấy context trước đó ra.

---

## Template

```java
Deque<Integer> countStack = new ArrayDeque<>();
Deque<StringBuilder> stringStack = new ArrayDeque<>();
```

---

## Implementation

```java
class Solution {
    public String decodeString(String s) {

        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> stringStack = new ArrayDeque<>();

        StringBuilder current = new StringBuilder();

        int number = 0;

        for (char c : s.toCharArray()) {

            if (Character.isDigit(c)) {

                number = number * 10 + (c - '0');

            } else if (c == '[') {

                countStack.push(number);
                stringStack.push(current);

                number = 0;
                current = new StringBuilder();

            } else if (c == ']') {

                int repeat = countStack.pop();
                StringBuilder previous = stringStack.pop();

                previous.append(
                    current.toString().repeat(repeat)
                );

                current = previous;

            } else {

                current.append(c);
            }
        }

        return current.toString();
    }
}
```

---

# Pattern 6 — Stack With Extra State

Không nhất thiết Stack chỉ lưu:

```java
Integer
```

Một phần tử Stack có thể lưu:

```text
(value, min)
(value, max)
(node, depth)
(index, state)
```

---

# LeetCode 155 — Min Stack

Yêu cầu:

```text
push()
pop()
top()
getMin()
```

tất cả phải:

```text
O(1)
```

---

## Cách 1: hai Stack

```text
normalStack
minStack
```

Mỗi khi push `x`:

```text
normalStack.push(x)

if minStack empty
or x <= minStack.top
    minStack.push(x)
```

---

## Implementation

```java
class MinStack {

    private final Deque<Integer> stack;
    private final Deque<Integer> minStack;

    public MinStack() {
        stack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(int val) {

        stack.push(val);

        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    public void pop() {

        int value = stack.pop();

        if (value == minStack.peek()) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
```

---

# Phần II — Monotonic Stack

Đây là phần quan trọng nhất.

---

# 1. Monotonic Stack là gì?

Monotonic Stack là Stack mà các phần tử được duy trì theo một thứ tự đơn điệu:

```text
increasing
```

hoặc:

```text
decreasing
```

Ví dụ increasing stack:

```text
1
3
5
8
```

Decreasing stack:

```text
10
8
6
2
```

---

# 2. Vì sao cần Monotonic Stack?

Xét bài:

```text
nums = [2, 1, 2, 4, 3]
```

Với mỗi phần tử, tìm phần tử lớn hơn đầu tiên bên phải.

Brute force:

```java
for i:
    for j = i + 1:
        if nums[j] > nums[i]:
```

Complexity:

```text
O(n²)
```

Monotonic Stack có thể giảm xuống:

```text
O(n)
```

---

# 3. Câu hỏi cốt lõi

Hầu hết Monotonic Stack problems đều là biến thể của:

```text
Next Greater
Previous Greater
Next Smaller
Previous Smaller
```

Bạn nên thuộc bốn dạng này.

---

# Pattern A — Next Greater Element

Cho:

```text
[2, 1, 2, 4, 3]
```

Tìm phần tử lớn hơn đầu tiên bên phải.

```text
2 → 4
1 → 2
2 → 4
4 → -1
3 → -1
```

Answer:

```text
[4, 2, 4, -1, -1]
```

---

# Cách suy luận quan trọng

Ta scan:

```text
left → right
```

Stack chứa những phần tử:

> vẫn đang chờ tìm Next Greater Element.

Ví dụ:

```text
nums = [2, 1, 2, 4]
```

Ban đầu:

```text
stack = []
```

---

## i = 0, nums[i] = 2

```text
stack = [2]
```

---

## i = 1, nums[i] = 1

`1` không giải quyết được `2`.

```text
stack = [2, 1]
```

---

## i = 2, nums[i] = 2

`2 > 1`

Ta vừa tìm được Next Greater của `1`.

```text
1 → 2
```

pop `1`.

Stack:

```text
[2]
```

Current `2` không greater than previous `2`.

push.

```text
[2, 2]
```

---

## i = 3, nums[i] = 4

```text
4 > 2
```

nên:

```text
2 → 4
2 → 4
```

pop hết.

---

# Template quan trọng nhất

Đừng lưu value.

Thông thường hãy lưu:

```text
INDEX
```

vì ta thường cần:

```text
distance
answer[index]
nums[index]
```

---

## Next Greater Template

```java
int n = nums.length;

int[] answer = new int[n];
Arrays.fill(answer, -1);

Deque<Integer> stack = new ArrayDeque<>();

for (int i = 0; i < n; i++) {

    while (
        !stack.isEmpty() &&
        nums[i] > nums[stack.peek()]
    ) {

        int index = stack.pop();

        answer[index] = nums[i];
    }

    stack.push(i);
}
```

---

# Mental model

Điều cực kỳ quan trọng:

```text
Current element = người giải quyết
Stack elements = những người đang chờ câu trả lời
```

Trong:

```java
while (nums[i] > nums[stack.peek()])
```

`nums[i]` chính là Next Greater của các phần tử bị pop.

---

# LeetCode 739 — Daily Temperatures

Đây gần như là template Next Greater nguyên bản.

Cho:

```text
temperatures =
[73,74,75,71,69,72,76,73]
```

Output:

```text
[1,1,4,2,1,1,0,0]
```

Không phải hỏi:

> nhiệt độ cao hơn tiếp theo là bao nhiêu?

Mà hỏi:

> phải chờ bao nhiêu ngày?

Do đó lưu index.

---

## Algorithm

Stack chứa index của những ngày:

> chưa tìm được ngày nóng hơn.

Khi:

```java
temperature[i] > temperature[stack.peek()]
```

ta đã tìm thấy câu trả lời.

Khoảng cách:

```java
i - previousIndex
```

---

## Implementation

```java
class Solution {

    public int[] dailyTemperatures(int[] temperatures) {

        int n = temperatures.length;

        int[] answer = new int[n];

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {

            while (
                !stack.isEmpty() &&
                temperatures[i] > temperatures[stack.peek()]
            ) {

                int prev = stack.pop();

                answer[prev] = i - prev;
            }

            stack.push(i);
        }

        return answer;
    }
}
```

Complexity:

```text
Time: O(n)
Space: O(n)
```

---

# Tại sao không phải O(n²)?

Bạn nhìn thấy:

```java
for (...)
    while (...)
```

có thể nghĩ:

```text
O(n²)
```

Nhưng không.

Mỗi index:

```text
push đúng 1 lần
pop tối đa 1 lần
```

Tổng số:

```text
n push + n pop
```

Do đó:

```text
O(n)
```

Đây là **amortized analysis**.

---

# Pattern B — Previous Greater Element

Bây giờ hỏi:

> phần tử lớn hơn gần nhất bên trái là gì?

Ví dụ:

```text
[2, 1, 4, 3]
```

Result:

```text
2 → none
1 → 2
4 → none
3 → 4
```

---

## Template

Khác Next Greater một chút.

Current element đang đi tìm answer cho **chính nó**.

```java
for (int i = 0; i < n; i++) {

    while (
        !stack.isEmpty() &&
        nums[stack.peek()] <= nums[i]
    ) {
        stack.pop();
    }

    if (!stack.isEmpty()) {
        answer[i] = nums[stack.peek()];
    }

    stack.push(i);
}
```

---

# Sự khác biệt cực kỳ quan trọng

## Next Greater

Current giải quyết các phần tử cũ:

```java
while (nums[i] > nums[stack.peek()]) {

    int index = stack.pop();

    answer[index] = nums[i];
}
```

---

## Previous Greater

Current tự tìm answer trong stack:

```java
while (nums[stack.peek()] <= nums[i]) {
    stack.pop();
}

answer[i] = nums[stack.peek()];
```

Đây là cách phân biệt rất hữu ích.

---

# Pattern C — Next Smaller Element

Chỉ cần đảo dấu.

```java
while (
    !stack.isEmpty() &&
    nums[i] < nums[stack.peek()]
) {

    int index = stack.pop();

    answer[index] = nums[i];
}

stack.push(i);
```

---

# Pattern D — Previous Smaller Element

```java
while (
    !stack.isEmpty() &&
    nums[stack.peek()] >= nums[i]
) {
    stack.pop();
}

if (!stack.isEmpty()) {
    answer[i] = nums[stack.peek()];
}

stack.push(i);
```

---

# Bảng template cần thuộc

| Problem          | Scan         | Pop condition        |
| ---------------- | ------------ | -------------------- |
| Next Greater     | left → right | `current > top`      |
| Next Smaller     | left → right | `current < top`      |
| Previous Greater | left → right | pop `top <= current` |
| Previous Smaller | left → right | pop `top >= current` |

Nhưng quan trọng hơn việc học bảng là hiểu:

> **Bạn đang loại bỏ những phần tử nào vì chúng không còn hữu ích?**

---

# Pattern E — Circular Monotonic Stack

## LeetCode 503 — Next Greater Element II

Array được coi là circular.

Ví dụ:

```text
[1, 2, 1]
```

Với phần tử cuối:

```text
1
```

ta được phép vòng lại đầu:

```text
1 → 2
```

Answer:

```text
[2, -1, 2]
```

---

## Trick

Thay vì thực sự copy:

```text
[1,2,1,1,2,1]
```

ta loop:

```java
for (int i = 0; i < 2 * n; i++)
```

index thật:

```java
int index = i % n;
```

---

## Implementation

```java
class Solution {

    public int[] nextGreaterElements(int[] nums) {

        int n = nums.length;

        int[] answer = new int[n];
        Arrays.fill(answer, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < 2 * n; i++) {

            int index = i % n;

            while (
                !stack.isEmpty() &&
                nums[index] > nums[stack.peek()]
            ) {

                int prev = stack.pop();

                answer[prev] = nums[index];
            }

            if (i < n) {
                stack.push(index);
            }
        }

        return answer;
    }
}
```

Điểm quan trọng:

```java
if (i < n)
```

Chỉ push phần tử trong vòng đầu.

Vòng thứ hai chỉ dùng để resolve những phần tử chưa có answer.

---

# Pattern F — Stock Span

## LeetCode 901 — Online Stock Span

Giá hôm nay:

```text
price = 100, 80, 60, 70, 60, 75, 85
```

Mỗi ngày hỏi:

> Có bao nhiêu ngày liên tiếp ngược về quá khứ mà giá <= giá hôm nay?

Output:

```text
1, 1, 1, 2, 1, 4, 6
```

---

## Insight

Đây thực chất là:

> tìm Previous Greater Element.

Nếu Previous Greater nằm tại:

```text
j
```

thì:

```text
span = i - j
```

---

## Một implementation hay hơn

Thay vì lưu từng index, có thể lưu:

```text
(price, span)
```

Ví dụ:

```java
class StockSpanner {

    Deque<int[]> stack;

    public StockSpanner() {
        stack = new ArrayDeque<>();
    }

    public int next(int price) {

        int span = 1;

        while (
            !stack.isEmpty() &&
            stack.peek()[0] <= price
        ) {

            span += stack.pop()[1];
        }

        stack.push(new int[]{price, span});

        return span;
    }
}
```

Đây là một pattern rất đẹp:

> Khi pop một phần tử, ta hấp thụ luôn thông tin mà nó đã tổng hợp.

---

# Pattern G — Histogram / Nearest Smaller Boundary

Đây là một trong những ứng dụng quan trọng nhất của Monotonic Stack.

# LeetCode 84 — Largest Rectangle in Histogram

Cho:

```text
heights = [2,1,5,6,2,3]
```

Tìm rectangle lớn nhất.

Answer:

```text
10
```

từ:

```text
5, 6
```

height:

```text
5
```

width:

```text
2
```

area:

```text
10
```

---

# Cách suy luận

Giả sử ta chọn:

```text
height[i]
```

làm chiều cao rectangle.

Ta muốn mở rộng:

```text
← left
right →
```

cho đến khi gặp một bar:

```text
height < height[i]
```

Vì bar thấp hơn sẽ chặn rectangle.

Do đó ta cần:

```text
Previous Smaller
Next Smaller
```

---

# Công thức

Nếu:

```text
left[i]  = previous smaller index
right[i] = next smaller index
```

thì:

```text
width = right[i] - left[i] - 1
```

và:

```text
area =
height[i] * width
```

---

# Ví dụ

```text
heights:

index   0 1 2 3 4 5
        2 1 5 6 2 3
```

Với:

```text
i = 2
height = 5
```

Previous smaller:

```text
index 1
height = 1
```

Next smaller:

```text
index 4
height = 2
```

Do đó:

```text
width
= 4 - 1 - 1
= 2
```

Area:

```text
5 * 2 = 10
```

---

# Solution 1 — tính left và right

```java
class Solution {

    public int largestRectangleArea(int[] heights) {

        int n = heights.length;

        int[] left = new int[n];
        int[] right = new int[n];

        Deque<Integer> stack = new ArrayDeque<>();

        // Previous smaller
        for (int i = 0; i < n; i++) {

            while (
                !stack.isEmpty() &&
                heights[stack.peek()] >= heights[i]
            ) {
                stack.pop();
            }

            left[i] =
                stack.isEmpty() ? -1 : stack.peek();

            stack.push(i);
        }

        stack.clear();

        // Next smaller
        for (int i = n - 1; i >= 0; i--) {

            while (
                !stack.isEmpty() &&
                heights[stack.peek()] >= heights[i]
            ) {
                stack.pop();
            }

            right[i] =
                stack.isEmpty() ? n : stack.peek();

            stack.push(i);
        }

        int maxArea = 0;

        for (int i = 0; i < n; i++) {

            int width =
                right[i] - left[i] - 1;

            int area =
                heights[i] * width;

            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }
}
```

Complexity:

```text
Time: O(n)
Space: O(n)
```

---

# Solution 2 — One-pass Monotonic Stack

Đây là version nên hiểu sâu.

Ta duy trì increasing stack.

Khi gặp:

```text
current height < stack.top height
```

nghĩa là rectangle sử dụng `stack.top` làm minimum không thể mở rộng thêm nữa.

Vậy đây là lúc tính area.

---

## Template

```java
for (int i = 0; i <= n; i++) {

    int currentHeight =
        i == n ? 0 : heights[i];

    while (
        !stack.isEmpty() &&
        currentHeight < heights[stack.peek()]
    ) {

        int height = heights[stack.pop()];

        int left =
            stack.isEmpty()
            ? -1
            : stack.peek();

        int width =
            i - left - 1;

        maxArea =
            Math.max(maxArea, height * width);
    }

    stack.push(i);
}
```

Sentinel:

```text
height = 0
```

ở cuối giúp pop hết stack.

---

## Full code

```java
class Solution {

    public int largestRectangleArea(int[] heights) {

        int n = heights.length;

        Deque<Integer> stack = new ArrayDeque<>();

        int maxArea = 0;

        for (int i = 0; i <= n; i++) {

            int currentHeight =
                i == n ? 0 : heights[i];

            while (
                !stack.isEmpty() &&
                currentHeight < heights[stack.peek()]
            ) {

                int height =
                    heights[stack.pop()];

                int left =
                    stack.isEmpty()
                    ? -1
                    : stack.peek();

                int width =
                    i - left - 1;

                maxArea =
                    Math.max(
                        maxArea,
                        height * width
                    );
            }

            stack.push(i);
        }

        return maxArea;
    }
}
```

---

# Pattern H — Monotonic Stack + Contribution

Đây là pattern nâng cao cực kỳ quan trọng.

Các bài như:

```text
907. Sum of Subarray Minimums
2104. Sum of Subarray Ranges
1856. Maximum Subarray Min-Product
```

Không hỏi:

> next smaller là gì?

Mà hỏi:

> mỗi phần tử đóng góp vào bao nhiêu subarray?

---

# LeetCode 907 — Sum of Subarray Minimums

Cho:

```text
arr = [3,1,2,4]
```

Các subarray:

```text
[3]       min = 3
[1]       min = 1
[2]       min = 2
[4]       min = 4

[3,1]     min = 1
[1,2]     min = 1
[2,4]     min = 2

[3,1,2]   min = 1
[1,2,4]   min = 1

[3,1,2,4] min = 1
```

Tổng:

```text
17
```

Brute force rất chậm.

---

# Đổi perspective

Thay vì:

> Với mỗi subarray tìm minimum.

Ta hỏi:

> Với mỗi `arr[i]`, có bao nhiêu subarray mà `arr[i]` là minimum?

---

## Ví dụ

Giả sử:

```text
arr[i] = 2
```

Ta tìm boundary:

```text
Previous Smaller
Next Smaller
```

Giả sử:

```text
left = previous smaller index
right = next smaller index
```

Số cách chọn start:

```text
i - left
```

Số cách chọn end:

```text
right - i
```

Tổng số subarray:

```text
(i - left) * (right - i)
```

Contribution:

```text
arr[i]
*
(i - left)
*
(right - i)
```

Đây là công thức cực kỳ quan trọng.

---

# Nhưng duplicates gây vấn đề

Ví dụ:

```text
[2,2]
```

Nếu cả hai bên đều dùng strict:

```text
previous smaller
next smaller
```

có thể duplicate counting.

Do đó thường phải dùng một bên strict và một bên non-strict.

Ví dụ:

```text
previous less
next less or equal
```

hoặc ngược lại.

Đây là điểm rất hay bị sai.

---

# Template Contribution

```java
left[i] = distance to previous less
right[i] = distance to next less-or-equal

contribution =
arr[i] * left[i] * right[i]
```

---

# Implementation

```java
class Solution {

    public int sumSubarrayMins(int[] arr) {

        int n = arr.length;

        int MOD = 1_000_000_007;

        int[] left = new int[n];
        int[] right = new int[n];

        Deque<Integer> stack = new ArrayDeque<>();

        // Previous strictly smaller
        for (int i = 0; i < n; i++) {

            while (
                !stack.isEmpty() &&
                arr[stack.peek()] >= arr[i]
            ) {
                stack.pop();
            }

            left[i] =
                stack.isEmpty()
                ? i + 1
                : i - stack.peek();

            stack.push(i);
        }

        stack.clear();

        // Next smaller or equal
        for (int i = n - 1; i >= 0; i--) {

            while (
                !stack.isEmpty() &&
                arr[stack.peek()] > arr[i]
            ) {
                stack.pop();
            }

            right[i] =
                stack.isEmpty()
                ? n - i
                : stack.peek() - i;

            stack.push(i);
        }

        long answer = 0;

        for (int i = 0; i < n; i++) {

            long contribution =
                (long) arr[i]
                * left[i]
                * right[i];

            answer =
                (answer + contribution) % MOD;
        }

        return (int) answer;
    }
}
```

---

# Pattern I — Sum of Subarray Ranges

## LeetCode 2104

Range:

```text
max - min
```

Ta cần:

```text
sum(max of every subarray)
-
sum(min of every subarray)
```

Tức là dùng Contribution Technique hai lần:

```text
Contribution as maximum
Contribution as minimum
```

---

## Tư tưởng tổng quát

Nếu muốn tính:

```text
sum of subarray minimums
```

→ tìm smaller boundaries.

Nếu muốn:

```text
sum of subarray maximums
```

→ tìm greater boundaries.

Đây là một pattern cực mạnh.

---

# Pattern J — Monotonic Stack + Greedy

Không phải Monotonic Stack nào cũng để tìm next/previous.

Có một nhóm:

> Duy trì answer tối ưu bằng cách xóa các phần tử xấu khỏi stack.

Ví dụ nổi tiếng:

```text
402. Remove K Digits
316. Remove Duplicate Letters
1081. Smallest Subsequence of Distinct Characters
```

---

# LeetCode 402 — Remove K Digits

Cho:

```text
num = "1432219"
k = 3
```

Xóa 3 chữ số để số còn lại nhỏ nhất.

Answer:

```text
1219
```

---

# Insight

Để số nhỏ nhất, ta muốn chữ số nhỏ xuất hiện càng sớm càng tốt.

Ví dụ:

```text
1 4 3
```

Khi gặp `3`:

```text
4 > 3
```

Nếu còn quyền xóa:

```text
remove 4
```

bởi:

```text
13 < 14
```

---

# Stack

Ta duy trì:

```text
monotonic increasing stack
```

Khi:

```java
stack.top > current
```

và:

```java
k > 0
```

ta pop.

---

## Implementation

```java
class Solution {

    public String removeKdigits(String num, int k) {

        Deque<Character> stack = new ArrayDeque<>();

        for (char digit : num.toCharArray()) {

            while (
                !stack.isEmpty() &&
                k > 0 &&
                stack.peekLast() > digit
            ) {

                stack.removeLast();
                k--;
            }

            stack.addLast(digit);
        }

        while (k > 0) {
            stack.removeLast();
            k--;
        }

        StringBuilder result = new StringBuilder();

        boolean leadingZero = true;

        for (char digit : stack) {

            if (leadingZero && digit == '0') {
                continue;
            }

            leadingZero = false;
            result.append(digit);
        }

        return result.length() == 0
            ? "0"
            : result.toString();
    }
}
```

---

# Pattern K — Trapping Rain Water với Monotonic Stack

## LeetCode 42

Đây là một bài rất hay vì có nhiều solution:

```text
brute force
prefix max
two pointers
monotonic stack
```

Monotonic Stack solution giúp hiểu boundary rất tốt.

---

## Concept

Ví dụ:

```text
height:

      █
  █~~~█
█~█~~~█
```

Khi gặp một bar cao hơn top của stack:

```text
current > stack.top
```

ta có khả năng vừa tìm được:

```text
right boundary
```

cho một vùng nước.

---

## Process

```java
while (
    !stack.isEmpty() &&
    height[i] > height[stack.peek()]
)
```

Pop:

```java
bottom = stack.pop();
```

Nếu stack empty:

```text
không có left wall
```

stop.

Nếu còn:

```text
left wall  = stack.peek()
right wall = i
bottom     = popped index
```

Width:

```text
i - left - 1
```

Water height:

```text
min(height[left], height[i])
-
height[bottom]
```

Volume:

```text
width * boundedHeight
```

---

## Implementation

```java
class Solution {

    public int trap(int[] height) {

        Deque<Integer> stack = new ArrayDeque<>();

        int water = 0;

        for (int i = 0; i < height.length; i++) {

            while (
                !stack.isEmpty() &&
                height[i] > height[stack.peek()]
            ) {

                int bottom = stack.pop();

                if (stack.isEmpty()) {
                    break;
                }

                int left = stack.peek();

                int width =
                    i - left - 1;

                int boundedHeight =
                    Math.min(
                        height[left],
                        height[i]
                    )
                    - height[bottom];

                water += width * boundedHeight;
            }

            stack.push(i);
        }

        return water;
    }
}
```

---

# Một khái niệm cực quan trọng: Stack lưu VALUE hay INDEX?

Trong Monotonic Stack, mặc định nên nghĩ:

```text
INDEX
```

chứ không phải value.

Ví dụ:

```java
Deque<Integer> stack;
```

nhưng integer ở đây là:

```text
index
```

---

## Vì sao?

Bởi index cho ta cả hai:

Value:

```java
nums[index]
```

Position:

```java
index
```

Distance:

```java
i - index
```

Boundary:

```java
right - left - 1
```

Trong khi lưu value chỉ cho:

```text
value
```

và mất position.

---

# Một vấn đề cực quan trọng: `<` hay `<=`?

Đây là lỗi phổ biến nhất trong Monotonic Stack.

Ví dụ:

```text
[2,2,2]
```

Bạn phải quyết định:

```text
equal values được giữ hay pop?
```

---

# Strictly increasing stack

Nếu viết:

```java
while (
    !stack.isEmpty() &&
    nums[stack.peek()] >= nums[i]
) {
    stack.pop();
}
```

thì sau loop:

```text
stack.top < current
```

Stack là:

```text
strictly increasing
```

---

# Non-decreasing stack

Nếu:

```java
while (
    !stack.isEmpty() &&
    nums[stack.peek()] > nums[i]
) {
    stack.pop();
}
```

thì equal được giữ.

Stack:

```text
non-decreasing
```

Ví dụ:

```text
1, 2, 2, 4
```

---

# Khi nào điều này đặc biệt quan trọng?

Các bài:

```text
Largest Rectangle
Sum of Subarray Minimums
Sum of Subarray Ranges
```

vì duplicate values có thể:

```text
double count
```

---

# Một cách nhớ Monotonic Stack tốt hơn

Đừng học:

```text
greater → decreasing
smaller → increasing
```

một cách máy móc.

Hãy tự hỏi:

## Câu hỏi 1

```text
Ai đang chờ câu trả lời?
```

Ví dụ Daily Temperatures:

```text
stack chứa những ngày chưa có ngày nóng hơn
```

---

## Câu hỏi 2

```text
Current element giải quyết được ai?
```

Nếu:

```text
current > stack.top
```

thì current giải quyết được stack.top.

---

## Câu hỏi 3

```text
Khi nào một element trở nên vô dụng?
```

Ví dụ tìm previous smaller:

Nếu:

```text
top >= current
```

thì `top` không thể là previous smaller của current.

Hơn nữa current còn nằm gần hơn cho các phần tử tương lai.

Do đó:

```text
pop top
```

---

# Một ví dụ rất quan trọng

Cho:

```text
[2, 7, 3]
```

Giả sử đang xây increasing stack để tìm previous smaller.

Khi `3` đến:

```text
stack = [2,7]
```

`7 >= 3`

→ `7` không thể là previous smaller của `3`.

Pop.

Còn:

```text
2 < 3
```

→ previous smaller của `3` là `2`.

Stack trở thành:

```text
[2,3]
```

Tại sao xóa `7` vĩnh viễn được?

Vì đối với một phần tử tương lai `x`:

Nếu:

```text
x > 7
```

thì `3` cũng:

```text
3 < x
```

và `3` gần `x` hơn `7`.

Nếu:

```text
x <= 7
```

thì `7` không phải smaller.

Vậy `7` không còn hữu ích.

Đây chính là bản chất của Monotonic Stack:

> **Pop những candidate bị một candidate tốt hơn dominate.**

---

# Pattern L — Monotonic Stack theo hướng scan ngược

Không phải lúc nào cũng phải scan:

```text
left → right
```

Ví dụ muốn tìm Next Greater trực tiếp cho current, có thể scan:

```text
right → left
```

---

## Template Next Greater — reverse scan

```java
for (int i = n - 1; i >= 0; i--) {

    while (
        !stack.isEmpty() &&
        nums[stack.peek()] <= nums[i]
    ) {
        stack.pop();
    }

    answer[i] =
        stack.isEmpty()
        ? -1
        : nums[stack.peek()];

    stack.push(i);
}
```

Ở đây:

```text
stack chứa candidates ở bên phải
```

Current tự tìm answer.

---

# Hai style của Monotonic Stack

## Style 1 — Resolve previous elements

```text
scan left → right
current resolves stack
```

Ví dụ:

```java
while (current > top) {
    answer[top] = current;
    pop;
}
```

Rất phù hợp:

```text
Daily Temperatures
Next Greater Element
```

---

## Style 2 — Query stack for current

```text
scan theo hướng opposite
clean stack
top becomes answer
```

Ví dụ:

```java
while (top <= current) {
    pop;
}

answer[current] = top;
```

Rất phù hợp:

```text
Previous Greater
Previous Smaller
Boundary problems
```

---

# Stack vs Monotonic Stack

## Stack thông thường

Không có yêu cầu order.

Ví dụ:

```text
[
    '(',
    '[',
    '{'
]
```

Ta chỉ quan tâm LIFO.

---

## Monotonic Stack

Stack phải duy trì:

```text
increasing
```

hoặc:

```text
decreasing
```

Do đó trước khi push:

```java
while (condition) {
    stack.pop();
}
```

---

# Template tổng quát Monotonic Stack

Đây là template quan trọng nhất để nhớ:

```java
Deque<Integer> stack = new ArrayDeque<>();

for (int i = 0; i < n; i++) {

    while (
        !stack.isEmpty() &&
        SHOULD_POP(nums[stack.peek()], nums[i])
    ) {

        int index = stack.pop();

        // process index
    }

    // optional:
    // answer[i] = stack.peek();

    stack.push(i);
}
```

Hầu hết bài Monotonic Stack đều là biến thể của template này.

---

# Cách nhận diện Monotonic Stack trong interview

Nếu đề hỏi những câu kiểu:

```text
next greater
next smaller
previous greater
previous smaller
nearest greater
nearest smaller
first larger element
first warmer day
days until greater
nearest boundary
how far can element expand
```

→ Monotonic Stack nên xuất hiện ngay trong đầu.

Ngoài ra nếu thấy:

```text
for every element...
nearest element satisfying condition...
```

và brute force của bạn là:

```text
O(n²)
```

thì rất có khả năng Monotonic Stack biến nó thành:

```text
O(n)
```

---

# Các biến thể ngôn ngữ trong đề

Đề không nhất thiết nói:

> Find Next Greater Element.

Daily Temperatures nói:

> How many days until a warmer temperature?

Nhưng bản chất:

```text
Next Greater Temperature
```

Stock Span nói:

> consecutive days price <= current

Bản chất:

```text
Previous Greater
```

Largest Rectangle nói:

> maximum rectangle

Bản chất:

```text
Previous Smaller
+
Next Smaller
```

Sum of Subarray Minimums nói:

> sum minimum of every subarray

Bản chất:

```text
Previous Smaller
+
Next Smaller
+
Contribution
```

Kỹ năng quan trọng là **transform đề bài về một trong các primitive patterns**.

---

# Problem Map nên học

Mình đề xuất học theo thứ tự sau.

## Level 1 — Stack fundamentals

### 20. Valid Parentheses

Pattern:

```text
Matching Stack
```

Học:

```text
push opening
match closing
```

---

### 1047. Remove All Adjacent Duplicates

Pattern:

```text
Cancellation Stack
```

Học:

```text
current vs top
```

---

### 150. Evaluate Reverse Polish Notation

Pattern:

```text
Expression Stack
```

Học:

```text
operand stack
operator consumes operands
```

---

### 155. Min Stack

Pattern:

```text
Stack + auxiliary state
```

---

### 71. Simplify Path

Pattern:

```text
Stack Simulation
```

Ví dụ:

```text
/home/../foo
```

`..`:

```text
pop previous directory
```

---

### 735. Asteroid Collision

Pattern:

```text
Simulation + repeated collision
```

---

# Level 2 — Monotonic Stack Fundamentals

## 496. Next Greater Element I

Học template:

```text
Next Greater
```

---

## 739. Daily Temperatures

Học:

```text
Next Greater + index distance
```

Đây là bài **bắt buộc phải thành thạo**.

---

## 503. Next Greater Element II

Học:

```text
Circular Monotonic Stack
```

---

## 901. Online Stock Span

Học:

```text
Previous Greater
compressed stack
```

---

# Level 3 — Boundary Problems

## 84. Largest Rectangle in Histogram

Học:

```text
Previous Smaller
Next Smaller
Boundary expansion
```

Đây là bài quan trọng nhất của Monotonic Stack.

---

## 85. Maximal Rectangle

Bài 2D.

Mỗi row được biến thành histogram:

```text
1 0 1 0
1 0 1 1
1 1 1 1
```

height:

```text
row1: 1 0 1 0
row2: 2 0 2 1
row3: 3 1 3 2
```

Mỗi row gọi:

```text
Largest Rectangle in Histogram
```

Đây là pattern:

```text
2D problem
↓
transform to repeated 1D histogram
↓
Monotonic Stack
```

---

# Level 4 — Advanced

## 42. Trapping Rain Water

Pattern:

```text
Monotonic decreasing stack
boundary calculation
```

---

## 402. Remove K Digits

Pattern:

```text
Greedy + Monotonic Increasing Stack
```

---

## 316. Remove Duplicate Letters

Pattern:

```text
Greedy
+
Monotonic Stack
+
frequency
+
visited
```

---

## 907. Sum of Subarray Minimums

Pattern:

```text
Boundary
+
Contribution
```

Rất quan trọng.

---

## 2104. Sum of Subarray Ranges

Pattern:

```text
Contribution as maximum
-
Contribution as minimum
```

---

# Bảng tổng hợp các pattern

| Pattern               | Ý tưởng                           | Bài tiêu biểu |
| --------------------- | --------------------------------- | ------------- |
| Matching              | match nearest opening             | 20            |
| Cancellation          | top + current cancel              | 1047          |
| Simulation            | trạng thái gần nhất trước         | 735           |
| Expression            | operands/operators                | 150           |
| Nested context        | save previous state               | 394           |
| Extra state           | stack + min/max                   | 155           |
| Next Greater          | current resolves smaller previous | 496, 739      |
| Circular NGE          | traverse `2n`                     | 503           |
| Previous Greater      | remove smaller/equal candidates   | 901           |
| Previous/Next Smaller | find boundaries                   | 84            |
| Histogram             | smaller boundaries                | 84, 85        |
| Water boundary        | pop valleys                       | 42            |
| Greedy Stack          | remove bad earlier candidate      | 402, 316      |
| Contribution          | count subarrays per element       | 907, 2104     |

---

# Template Cheat Sheet

## 1. Basic Stack

```java
Deque<Integer> stack = new ArrayDeque<>();

for (...) {

    if (...) {
        stack.push(x);
    }

    if (...) {
        stack.pop();
    }

    int top = stack.peek();
}
```

---

# 2. Next Greater Element

```java
Deque<Integer> stack = new ArrayDeque<>();

for (int i = 0; i < n; i++) {

    while (
        !stack.isEmpty() &&
        nums[i] > nums[stack.peek()]
    ) {

        int j = stack.pop();

        answer[j] = nums[i];
    }

    stack.push(i);
}
```

---

# 3. Next Smaller Element

```java
for (int i = 0; i < n; i++) {

    while (
        !stack.isEmpty() &&
        nums[i] < nums[stack.peek()]
    ) {

        int j = stack.pop();

        answer[j] = nums[i];
    }

    stack.push(i);
}
```

---

# 4. Previous Greater Element

```java
for (int i = 0; i < n; i++) {

    while (
        !stack.isEmpty() &&
        nums[stack.peek()] <= nums[i]
    ) {
        stack.pop();
    }

    answer[i] =
        stack.isEmpty()
        ? -1
        : nums[stack.peek()];

    stack.push(i);
}
```

---

# 5. Previous Smaller Element

```java
for (int i = 0; i < n; i++) {

    while (
        !stack.isEmpty() &&
        nums[stack.peek()] >= nums[i]
    ) {
        stack.pop();
    }

    answer[i] =
        stack.isEmpty()
        ? -1
        : nums[stack.peek()];

    stack.push(i);
}
```

---

# 6. Circular Array

```java
for (int i = 0; i < 2 * n; i++) {

    int index = i % n;

    ...

    if (i < n) {
        stack.push(index);
    }
}
```

---

# 7. Boundary / Histogram

```java
while (
    !stack.isEmpty() &&
    currentHeight < heights[stack.peek()]
) {

    int mid = stack.pop();

    int left =
        stack.isEmpty()
        ? -1
        : stack.peek();

    int right = i;

    int width =
        right - left - 1;

    ...
}
```

---

# 8. Contribution Template

Cho:

```text
previous boundary = left
next boundary     = right
```

số subarray chứa `i` mà `i` chịu trách nhiệm:

```text
(i - left)
*
(right - i)
```

Contribution:

```java
(long) nums[i]
* (i - left)
* (right - i);
```

---

# 9. Greedy Monotonic Stack

```java
for (int x : nums) {

    while (
        !stack.isEmpty() &&
        canRemove &&
        stack.peek() > x
    ) {

        stack.pop();
    }

    stack.push(x);
}
```

---

# Những lỗi phổ biến

## Lỗi 1 — lưu value thay vì index

Bạn viết:

```java
stack.push(nums[i]);
```

nhưng sau đó cần:

```text
distance
```

thì không còn biết vị trí.

Thông thường:

```java
stack.push(i);
```

tốt hơn.

---

# Lỗi 2 — hiểu sai monotonic direction

Tên:

```text
Next Greater Element
```

không có nghĩa Stack nhất định phải tên là "greater stack".

Hãy suy luận từ pop condition.

Ví dụ:

```java
while (current > top)
    pop
```

thì những phần tử nhỏ hơn bị remove.

Những phần tử còn lại có xu hướng:

```text
decreasing
```

---

# Lỗi 3 — quên process remaining stack

Một số bài những phần tử còn lại có answer mặc định:

```text
-1
0
n
```

Ví dụ Daily Temperatures:

```text
answer mặc định = 0
```

nên không cần process.

Histogram lại cần flush.

Do đó thêm sentinel:

```java
i == n ? 0 : heights[i]
```

---

# Lỗi 4 — sai `<` và `<=`

Đặc biệt với duplicates:

```text
[2,2,2]
```

Nếu contribution counting, sai inequality có thể:

```text
double count
```

Hãy xác định rõ:

```text
strictly smaller
smaller or equal
strictly greater
greater or equal
```

---

# Lỗi 5 — nghĩ `for + while = O(n²)`

Monotonic Stack:

```java
for (...)
    while (...)
```

nhưng:

```text
each element push once
each element pop at most once
```

nên:

```text
O(n)
```

---

# Lỗi 6 — chưa xác định stack invariant

Trước khi code nên nói được một câu:

> "The stack stores indices of elements that..."

Ví dụ Daily Temperatures:

> The stack stores indices of days that haven't found a warmer future day yet, and their temperatures are maintained in decreasing order.

Nếu bạn không nói được câu này, khả năng cao chưa hiểu Stack của mình đang lưu gì.

---

# Framework giải bài Monotonic Stack trong interview

Khi gặp bài mới, hãy đi qua 6 câu hỏi.

## Step 1 — Brute force là gì?

Ví dụ:

```text
For each i,
scan to the right until finding first greater.
```

Nếu:

```text
O(n²)
```

thì nghĩ Monotonic Stack.

---

## Step 2 — Đang tìm relation gì?

Một trong:

```text
Next Greater
Next Smaller
Previous Greater
Previous Smaller
```

hoặc combination.

---

## Step 3 — Stack lưu gì?

Hầu hết:

```text
indices
```

---

## Step 4 — Stack invariant là gì?

Ví dụ:

```text
decreasing temperatures
```

---

## Step 5 — Khi nào pop?

Đặt câu hỏi:

> Khi current xuất hiện, phần tử nào trong stack đã có câu trả lời hoặc không còn hữu ích?

---

## Step 6 — Khi pop, tính gì?

Ví dụ Daily Temperatures:

```java
answer[index] = i - index;
```

Histogram:

```java
width = i - left - 1;
```

Contribution:

```java
count =
(i - left) * (right - i);
```

---

# Cách explain bằng tiếng Anh trong coding interview

Với Daily Temperatures, bạn có thể nói:

> I'll maintain a monotonic decreasing stack of indices. Each index in the stack represents a day that has not found a warmer future day yet.

Khi gặp current:

> If the current temperature is greater than the temperature at the top of the stack, then the current day is the first warmer day for that previous index.

Sau đó:

> I pop that index and calculate the waiting time as `currentIndex - previousIndex`.

Complexity:

> Although there is a while loop inside the for loop, every index is pushed once and popped at most once, so the overall time complexity is O(n).

Đây là một cách explain rất chuẩn.

---

# Mental Map cuối cùng

Bạn có thể ghi nhớ Stack theo sơ đồ:

```text
                         STACK
                           │
          ┌────────────────┴─────────────────┐
          │                                  │
     NORMAL STACK                     MONOTONIC STACK
          │                                  │
   ┌──────┼────────┐             ┌───────────┼───────────┐
   │      │        │             │           │           │
Matching Simulation Nested      Greater     Smaller     Greedy
   │      │        │             │           │           │
   20    735      394         Next/Prev   Next/Prev     402
                                 │           │
                               739          84
                               503          907
                               901          2104
```

---

# Lộ trình luyện tập mình đề xuất

Đừng làm ngẫu nhiên. Làm theo thứ tự:

```text
Stage 1 — Stack
20  Valid Parentheses
1047 Remove Adjacent Duplicates
150 Evaluate Reverse Polish Notation
155 Min Stack
71  Simplify Path
735 Asteroid Collision
394 Decode String

        ↓

Stage 2 — Monotonic Stack Basic
496 Next Greater Element I
739 Daily Temperatures
503 Next Greater Element II
901 Online Stock Span

        ↓

Stage 3 — Boundary
84 Largest Rectangle in Histogram
85 Maximal Rectangle
42 Trapping Rain Water

        ↓

Stage 4 — Greedy
402 Remove K Digits
316 Remove Duplicate Letters

        ↓

Stage 5 — Contribution
907 Sum of Subarray Minimums
2104 Sum of Subarray Ranges
1856 Maximum Subarray Min-Product
```

Nếu bạn chỉ chọn **5 bài cốt lõi để hiểu toàn bộ Monotonic Stack**, mình sẽ chọn:

```text
739 Daily Temperatures
    ↓
503 Next Greater Element II
    ↓
901 Online Stock Span
    ↓
84 Largest Rectangle in Histogram
    ↓
907 Sum of Subarray Minimums
```

Năm bài này lần lượt dạy bạn:

```text
Next Greater
→ Circular Stack
→ Previous Greater
→ Boundary
→ Contribution
```

Sau khi thật sự hiểu 5 dạng này, phần lớn bài Monotonic Stack trong interview sẽ trở thành việc xác định **stack invariant + pop condition + thông tin cần tính khi pop**, thay vì phải nghĩ ra thuật toán từ đầu.
