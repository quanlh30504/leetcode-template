Đúng **về ý tưởng**, nhưng nên nhớ bằng **hướng giữ `mid`** thay vì nhớ tên cận:

### 🔑 Binary Search Boundary — Key

| Mục tiêu                  | Khi `mid` hợp lệ                       | Update        |
| ------------------------- | -------------------------------------- | ------------- |
| **Cận dưới / First True** | `mid` có thể là đáp án → **giữ `mid`** | `right = mid` |
| **Cận trên / Last True**  | `mid` có thể là đáp án → **giữ `mid`** | `left = mid`  |

### 🧠 Công thức nhớ

**Tìm bên trái → đẩy `right` về `mid`**

```java
if (condition(mid))
    right = mid;
else
    left = mid + 1;
```

→ **First True / Lower Bound**

**Tìm bên phải → đẩy `left` lên `mid`**

```java
if (condition(mid))
    left = mid;
else
    right = mid - 1;
```

→ **Last True / Upper Boundary**

> ⚠️ Không nên nhớ “`left = mid` là cận trên, `right = mid` là cận dưới” một cách máy móc.
> **Key thực sự:**
> **Muốn boundary nằm bên trái → `right = mid`.**
> **Muốn boundary nằm bên phải → `left = mid`.**

### Một câu để nhớ lâu

> **`right = mid` → ép đáp án về bên trái → First True / Lower Bound.**
> **`left = mid` → ép đáp án về bên phải → Last True / Upper Boundary.**
Để nhớ **đủ cả `while`, update và `return`**, bạn có thể chốt thành 2 pattern đối xứng sau:

### 🔑 1. First True / Lower Bound — tìm **cận trái**

Mẫu:

```java
int left = MIN;
int right = MAX;

while (left < right) {
    int mid = left + (right - left) / 2;

    if (condition(mid)) {
        right = mid;       // mid có thể là đáp án → giữ lại
    } else {
        left = mid + 1;    // mid chắc chắn không phải
    }
}

return left;
```

🧠 **Key:**

> **Tìm bên trái → `right = mid` → `while (left < right)` → `return left`**

Ví dụ:

```text
F F F F | T T T T
        ↑
      answer
```

→ **Lower Bound = first `nums[i] >= target`**

---

### 🔑 2. Last True / Upper Boundary — tìm **cận phải**

Mẫu:

```java
int left = MIN;
int right = MAX;

while (left < right) {
    int mid = left + (right - left + 1) / 2;

    if (condition(mid)) {
        left = mid;        // mid có thể là đáp án → giữ lại
    } else {
        right = mid - 1;   // mid chắc chắn không phải
    }
}

return left;
```

🧠 **Key:**

> **Tìm bên phải → `left = mid` → `while (left < right)` → `return left`**

Ví dụ:

```text
T T T T | F F F F
        ↑
      answer
```

→ **Last True = phần tử hợp lệ cuối cùng**

---

### ⚡ Cheat Sheet

```text
FIRST TRUE / LOWER BOUND
F F F | T T T
      ↑
      answer

while (left < right)
mid = left + (right-left)/2

true  → right = mid
false → left = mid + 1

return left
```

```text
LAST TRUE / UPPER BOUND
T T T | F F F
    ↑
    answer

while (left < right)
mid = left + (right-left+1)/2

true  → left = mid
false → right = mid - 1

return left
```

### 🧠 Một câu duy nhất để nhớ

> **Muốn tìm boundary bên trái:** `right = mid`, lấy **mid thấp**.
> **Muốn tìm boundary bên phải:** `left = mid`, lấy **mid cao**.
> Cả hai đều **`while (left < right)` + `return left`**.

⚠️ Điểm cực kỳ quan trọng: **`left = mid` phải dùng upper-mid (`+1`)**, nếu không có thể bị vòng lặp vô hạn khi `left + 1 == right`.
