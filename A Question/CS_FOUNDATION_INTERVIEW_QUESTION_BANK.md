# CS Foundation Interview Question Bank

**Tổng số câu hỏi: 583**

- **P0:** 107
- **P1:** 283
- **P2:** 161
- **P3:** 32

**Phần chính theo tài liệu ôn tập: 529 câu. Phần bổ sung từ CV: 54 câu.**

## Mức ưu tiên

- **P0 — Rất cao:** Nền tảng trọng yếu, câu mở đầu phổ biến, nên luyện trước.
- **P1 — Cao:** Cơ chế, so sánh và các câu hỏi tiếp nối quan trọng.
- **P2 — Trung bình:** Tình huống vận hành, edge cases và cơ chế sâu hơn.
- **P3 — Đào sâu:** Nội dung chuyên biệt, ít gặp hơn trong vòng foundation.

Ưu tiên phần chính dựa trên mức nhấn mạnh trong tài liệu, tầm quan trọng nền tảng và khả năng được hỏi tiếp. `[CV]` chỉ xuất hiện ở mục bổ sung cuối cùng.

Trong mỗi chủ đề con, `Chuỗi hỏi` là thứ tự luyện hội thoại; mức ưu tiên nằm ngay trên từng câu. Các câu hỏi giữ thuật ngữ kỹ thuật bằng tiếng Anh khi cần.

## Thống kê theo chủ đề

| Chủ đề | Tổng | P0 | P1 | P2 | P3 |
| --- | ---: | ---: | ---: | ---: | ---: |
| 1. Hệ điều hành, CPU & Bộ nhớ | 51 | 8 | 23 | 15 | 5 |
| 2. Concurrency & Mô hình thực thi Java | 69 | 9 | 32 | 25 | 3 |
| 3. Networking, HTTP & Chẩn đoán kết nối | 70 | 16 | 37 | 15 | 2 |
| 4. DNS & Service Discovery | 36 | 5 | 11 | 14 | 6 |
| 5. TLS, HTTPS & Ranh giới tin cậy | 25 | 5 | 9 | 10 | 1 |
| 6. Database & PostgreSQL | 67 | 14 | 29 | 16 | 8 |
| 7. Redis, Caching & Distributed Lock | 66 | 9 | 31 | 24 | 2 |
| 8. Kafka & Event-driven Messaging | 53 | 14 | 25 | 12 | 2 |
| 9. WebSocket & Hệ thống thời gian thực | 42 | 7 | 22 | 11 | 2 |
| 10. Docker, Containers & Vận hành | 50 | 7 | 31 | 11 | 1 |
| 11. Bổ sung từ CV — Các hướng có thể bị hỏi thêm | 54 | 13 | 33 | 8 | 0 |

---

# 1. Hệ điều hành, CPU & Bộ nhớ

Nguồn: [Memory/Avoid race condition in java.md](Computer%20Science/Memory/Avoid%20race%20condition%20in%20java.md); [Memory/Java platform thread vs virtual thread.md](Computer%20Science/Memory/Java%20platform%20thread%20vs%20virtual%20thread.md); [Memory/Thread Execution Model & Context Switching.md](Computer%20Science/Memory/Thread%20Execution%20Model%20%26%20Context%20Switching.md); [Memory/context switch advance.md](Computer%20Science/Memory/context%20switch%20advance.md); [Memory/heap and stack/how stack and why stack.md](Computer%20Science/Memory/heap%20and%20stack/how%20stack%20and%20why%20stack.md); [Memory/mem.md](Computer%20Science/Memory/mem.md); [Memory/mutex and semaphore.md](Computer%20Science/Memory/mutex%20and%20semaphore.md); [Memory/physical core and logical cpu/defination.md](Computer%20Science/Memory/physical%20core%20and%20logical%20cpu/defination.md).

## 1.1 Process, thread & Cô lập tài nguyên

**Chuỗi hỏi:** Q001 → Q002 → Q003 → Q004 → Q005 → Q006.

- [ ] Q001. **[P0]** Process và thread khác nhau như thế nào về vai trò, tài nguyên và trạng thái thực thi?
- [ ] Q002. **[P0]** Các thread trong cùng một process dùng chung những gì, và mỗi thread giữ riêng những gì?
- [ ] Q003. **[P1]** Vì sao tạo thread thường ít tốn kém hơn tạo process, và phải đánh đổi điều gì về khả năng cô lập lỗi?
- [ ] Q004. **[P1]** Hai process có thể giao tiếp bằng những cách nào; khi nào bạn chọn shared memory thay vì pipe hoặc socket?
- [ ] Q005. **[P2]** Hai process có thể cùng ánh xạ một vùng RAM vật lý không, và khi đó cần xử lý vấn đề đồng bộ nào?
- [ ] Q006. **[P1]** Một ngoại lệ Java không được xử lý trong một thread khác gì lỗi native nghiêm trọng đối với phần còn lại của process?

## 1.2 Stack, heap & Stack frame

**Chuỗi hỏi:** Q007 → Q008 → Q009 → Q010 → Q011 → Q012 → Q013 → Q014 → Q015.

- [ ] Q007. **[P0]** Stack và heap khác nhau thế nào về mục đích, quyền truy cập và vòng đời dữ liệu?
- [ ] Q008. **[P1]** Khi gọi một method, stack frame cần lưu những trạng thái nào để có thể tiếp tục thực thi và quay về caller?
- [ ] Q009. **[P1]** Nếu biến cục bộ b được khai báo sau a, method có cần pop b trước khi đọc a không; việc truy cập biến cục bộ thực sự diễn ra thế nào?
- [ ] Q010. **[P1]** Một biến tham chiếu cục bộ, object mà nó trỏ tới và một primitive field trong object đó nằm ở những vùng nhớ nào theo mô hình JVM?
- [ ] Q011. **[P2]** Operand stack của JVM khác gì call stack của thread và mảng biến cục bộ trong một frame?
- [ ] Q012. **[P1]** Vì sao cấu trúc stack phù hợp với lời gọi hàm lồng nhau, và vì sao thu hồi một frame đã hoàn tất thường đơn giản?
- [ ] Q013. **[P1]** Điều gì gây StackOverflowError, và bạn sẽ phân tích một lỗi do đệ quy quá sâu như thế nào?
- [ ] Q014. **[P1]** Khi method kết thúc, có thể kết luận object mà biến cục bộ từng tham chiếu đã được thu hồi chưa?
- [ ] Q015. **[P3]** Inlining, escape analysis và scalar replacement có thể làm bố trí bộ nhớ thực tế khác mô hình nhìn từ mã Java như thế nào?

## 1.3 Bộ nhớ ảo, phân trang & Dịch địa chỉ

**Chuỗi hỏi:** Q016 → Q017 → Q018 → Q019 → Q020 → Q021 → Q022 → Q023.

- [ ] Q016. **[P0]** Bộ nhớ ảo giải quyết vấn đề gì ngay cả khi máy vẫn còn nhiều RAM trống?
- [ ] Q017. **[P1]** Vì sao cùng một địa chỉ ảo có thể chỉ đến dữ liệu khác nhau trong hai process?
- [ ] Q018. **[P1]** Virtual page và physical frame khác nhau thế nào; MMU và page table tham gia dịch địa chỉ ra sao?
- [ ] Q019. **[P2]** Với page 4 KiB và địa chỉ ảo 0x12345, bạn xác định page number và offset thế nào; phần nào thay đổi khi dịch địa chỉ?
- [ ] Q020. **[P2]** Vì sao dùng page table nhiều cấp, và cách này đánh đổi gì về bộ nhớ và chi phí tra cứu?
- [ ] Q021. **[P1]** TLB lưu thông tin gì, và tại sao nó quan trọng với tốc độ truy cập bộ nhớ?
- [ ] Q022. **[P1]** TLB miss khác gì page fault; trường hợp nào thực sự cần đọc dữ liệu từ ổ đĩa?
- [ ] Q023. **[P3]** Khi nào huge pages có thể cải thiện hiệu năng, và có thể gây bất lợi gì về cấp phát hoặc sử dụng RAM?

## 1.4 Page fault, copy-on-write & Áp lực bộ nhớ

**Chuỗi hỏi:** Q024 → Q025 → Q026 → Q027 → Q028 → Q029.

- [ ] Q024. **[P1]** Bạn có thể mô tả quá trình xử lý page fault, kể cả khi địa chỉ truy cập không hợp lệ không?
- [ ] Q025. **[P1]** Minor page fault và major page fault khác nhau thế nào trên Linux, và ảnh hưởng đến latency ra sao?
- [ ] Q026. **[P2]** Vì sao lần chạm đầu tiên vào vùng nhớ vừa cấp phát vẫn có thể gây page fault khi RAM chưa đầy?
- [ ] Q027. **[P2]** Copy-on-write hoạt động thế nào khi parent và child cùng tham chiếu một page rồi một bên ghi vào page đó?
- [ ] Q028. **[P2]** Nếu p99 latency tăng cùng major page faults nhưng GC pause bình thường, bạn sẽ thu thập bằng chứng gì?
- [ ] Q029. **[P3]** Pre-touch heap trước khi nhận traffic đánh đổi thời gian khởi động với độ trễ khi chạy như thế nào?

## 1.5 Scheduling, context switch & CPU topology

**Chuỗi hỏi:** Q030 → Q031 → Q032 → Q033 → Q034 → Q035 → Q036 → Q037 → Q038 → Q039 → Q040 → Q041.

- [ ] Q030. **[P0]** Concurrency khác parallelism thế nào; một chương trình có thể concurrent trên một logical CPU không?
- [ ] Q031. **[P0]** Khi context switch xảy ra, hệ điều hành cần lưu và khôi phục những trạng thái nào?
- [ ] Q032. **[P1]** Ngoài lưu và khôi phục register, những yếu tố nào làm context switching tốn kém?
- [ ] Q033. **[P1]** Voluntary và involuntary context switch khác nhau thế nào; workload nào làm từng loại tăng lên?
- [ ] Q034. **[P1]** Chuyển giữa hai thread cùng process khác gì chuyển giữa thread thuộc hai process ở mức address space?
- [ ] Q035. **[P2]** Một system call có luôn gây chuyển sang thread khác không; mode switch và context switch khác nhau ở đâu?
- [ ] Q036. **[P2]** Mỗi context switch có luôn xóa CPU cache hoặc flush toàn bộ TLB không; điều đó phụ thuộc những gì?
- [ ] Q037. **[P1]** Máy có 8 physical cores và 16 logical CPUs cho bạn biết gì, và chưa đủ để kết luận gì về throughput?
- [ ] Q038. **[P2]** SMT khác time slicing của hệ điều hành như thế nào khi hai luồng lệnh dùng chung một physical core?
- [ ] Q039. **[P2]** Thread migration khác context switch trên cùng CPU thế nào, và vì sao migration có thể ảnh hưởng cache locality?
- [ ] Q040. **[P3]** Khi nào CPU affinity có lợi, và khi nào nó có thể làm cân bằng tải hoặc latency kém hơn?
- [ ] Q041. **[P3]** Khi thread chuyển qua NUMA node khác, chi phí truy cập bộ nhớ có thể thay đổi thế nào?

## 1.6 JVM, garbage collection & Chẩn đoán bộ nhớ

**Chuỗi hỏi:** Q042 → Q043 → Q044 → Q045 → Q046 → Q047 → Q048 → Q049 → Q050 → Q051.

- [ ] Q042. **[P0]** Garbage collector xác định một object có thể được thu hồi bằng cách nào?
- [ ] Q043. **[P1]** Những đối tượng tham chiếu nào có thể đóng vai trò GC roots; một vòng tham chiếu không còn truy cập được có được thu hồi không?
- [ ] Q044. **[P0]** Vì sao ứng dụng Java vẫn có thể memory leak dù có garbage collection?
- [ ] Q045. **[P1]** Bạn phân biệt allocation rate cao, tập dữ liệu sống lớn hợp lệ và giữ object ngoài ý muốn như thế nào?
- [ ] Q046. **[P1]** -Xms và -Xmx kiểm soát gì, và vì sao không thể coi heap size là toàn bộ RAM mà JVM sử dụng?
- [ ] Q047. **[P1]** OutOfMemoryError do hết Java heap khác gì lỗi không tạo được native thread hoặc không cấp phát được direct buffer?
- [ ] Q048. **[P2]** Khi nào direct buffers đáng sử dụng, và phải theo dõi những chi phí nào ngoài heap?
- [ ] Q049. **[P2]** Bạn sẽ dùng heap dump, retained size và thông tin native memory để điều tra RAM tăng dần như thế nào?
- [ ] Q050. **[P2]** Ở mức khái quát, G1 tổ chức heap và phân chia công việc giữa các pha concurrent với stop-the-world ra sao?
- [ ] Q051. **[P2]** GC và page fault có thể ảnh hưởng lẫn nhau như thế nào dù là hai cơ chế khác nhau?

---

# 2. Concurrency & Mô hình thực thi Java

Nguồn: [Memory/Avoid race condition in java.md](Computer%20Science/Memory/Avoid%20race%20condition%20in%20java.md); [Memory/Java platform thread vs virtual thread.md](Computer%20Science/Memory/Java%20platform%20thread%20vs%20virtual%20thread.md); [Memory/Thread Execution Model & Context Switching.md](Computer%20Science/Memory/Thread%20Execution%20Model%20%26%20Context%20Switching.md); [Memory/context switch advance.md](Computer%20Science/Memory/context%20switch%20advance.md); [Memory/heap and stack/how stack and why stack.md](Computer%20Science/Memory/heap%20and%20stack/how%20stack%20and%20why%20stack.md); [Memory/mem.md](Computer%20Science/Memory/mem.md); [Memory/mutex and semaphore.md](Computer%20Science/Memory/mutex%20and%20semaphore.md); [Memory/physical core and logical cpu/defination.md](Computer%20Science/Memory/physical%20core%20and%20logical%20cpu/defination.md).

## 2.1 Race condition, atomicity & Java Memory Model

**Chuỗi hỏi:** Q052 → Q053 → Q054 → Q055 → Q056 → Q057 → Q058 → Q059 → Q060 → Q061.

- [ ] Q052. **[P0]** Race condition là gì; hai thread cùng thực hiện counter++ có thể làm mất một lần tăng như thế nào?
- [ ] Q053. **[P0]** Atomicity, visibility và ordering khác nhau ra sao trong chương trình đa luồng?
- [ ] Q054. **[P1]** Data race theo Java Memory Model khác gì race condition ở mức nghiệp vụ khi từng operation riêng lẻ đã thread-safe?
- [ ] Q055. **[P0]** volatile bảo đảm những gì, và có làm counter++ trở thành thao tác an toàn giữa nhiều thread không?
- [ ] Q056. **[P1]** Happens-before có ý nghĩa gì; monitor, volatile, Thread.start và join thiết lập quan hệ này ra sao?
- [ ] Q057. **[P1]** Một thread thay đổi cờ dừng còn thread khác đọc cờ trong vòng lặp; chuyện gì có thể xảy ra nếu thiếu bảo đảm visibility?
- [ ] Q058. **[P1]** Trước khi chọn lock, bạn xác định invariant và toàn bộ thao tác cần được bảo vệ như thế nào?
- [ ] Q059. **[P2]** Object được công bố cho thread khác trước khi khởi tạo an toàn có thể gây ra hành vi gì?
- [ ] Q060. **[P2]** Vì sao double-checked locking cần xét đến publication và reordering?
- [ ] Q061. **[P2]** Một lỗi concurrency biến mất khi thêm logging; bạn sẽ kiểm chứng tính đúng đắn thế nào mà không dựa vào timing tình cờ?

## 2.2 Immutability, confinement & ThreadLocal

**Chuỗi hỏi:** Q062 → Q063 → Q064 → Q065 → Q066 → Q067.

- [ ] Q062. **[P1]** Khi nào immutability hoặc thread confinement giúp loại bỏ nhu cầu locking, và phải đánh đổi gì?
- [ ] Q063. **[P1]** Object có tự động thread-safe chỉ vì biến tham chiếu tới nó là biến cục bộ của method không?
- [ ] Q064. **[P2]** Một class chỉ có final fields vẫn có thể để lộ mutable state như thế nào?
- [ ] Q065. **[P1]** ThreadLocal có thể làm dữ liệu của request trước xuất hiện trong request sau khi dùng thread pool như thế nào?
- [ ] Q066. **[P2]** Request context thay đổi ra sao khi công việc được chuyển sang một executor khác?
- [ ] Q067. **[P2]** Với cấu hình đọc nhiều và cập nhật ít, bạn so sánh immutable snapshot với lock trên object mutable như thế nào?

## 2.3 Mutex, monitor & Lock contention

**Chuỗi hỏi:** Q068 → Q069 → Q070 → Q071 → Q072 → Q073 → Q074 → Q075 → Q076 → Q077 → Q078.

- [ ] Q068. **[P0]** Critical section là gì, và mutex bảo vệ nó như thế nào?
- [ ] Q069. **[P0]** Khi nào bạn chọn synchronized, khi nào chọn ReentrantLock?
- [ ] Q070. **[P1]** Hai method cùng sửa một object nhưng dùng hai lock khác nhau thì invariant chung được bảo vệ đến đâu?
- [ ] Q071. **[P1]** Reentrant lock nghĩa là gì; owner lấy cùng lock nhiều lần thì phải giải phóng thế nào?
- [ ] Q072. **[P1]** Khi có exception, việc giải phóng lock bằng synchronized khác gì dùng Lock tường minh?
- [ ] Q073. **[P1]** Khi nào cần timed hoặc interruptible lock acquisition, và xử lý việc không lấy được lock ra sao?
- [ ] Q074. **[P1]** Nếu giữ shared lock trong lúc gọi một dịch vụ mạng chậm, những request khác bị ảnh hưởng thế nào?
- [ ] Q075. **[P2]** Bạn so sánh một coarse-grained lock với lock theo key hoặc partition về correctness, contention và quản lý bộ nhớ như thế nào?
- [ ] Q076. **[P2]** Khi nào spinning có thể hợp lý hơn parking; lấy monitor không tranh chấp có nhất thiết phải vào kernel không?
- [ ] Q077. **[P2]** Khi nào ReadWriteLock có thể tốt hơn mutex, và khi nào nó lại làm throughput hoặc thời gian chờ của writer kém hơn?
- [ ] Q078. **[P3]** Khi dùng optimistic read của StampedLock, cần kiểm tra gì trước khi sử dụng kết quả đã đọc?

## 2.4 Atomic operation, CAS & Lock-free

**Chuỗi hỏi:** Q079 → Q080 → Q081 → Q082 → Q083 → Q084 → Q085.

- [ ] Q079. **[P1]** Compare-and-set hoạt động thế nào, và một cập nhật cạnh tranh làm CAS retry loop thay đổi ra sao?
- [ ] Q080. **[P1]** Khi nào AtomicInteger là đủ, và vì sao nhiều atomic fields riêng biệt chưa bảo vệ được invariant giữa các fields?
- [ ] Q081. **[P2]** AtomicReference kết hợp immutable state có thể biểu diễn một cập nhật nhiều trường ra sao; điều gì không nên lặp lại trong CAS loop?
- [ ] Q082. **[P2]** Lock-free bảo đảm sự tiến triển ở mức nào; một thread riêng lẻ vẫn có thể liên tục thất bại không?
- [ ] Q083. **[P2]** Vì sao CAS có thể chậm hơn lock khi contention cao?
- [ ] Q084. **[P3]** ABA problem là gì, và version hoặc stamp giúp phân biệt những lịch sử thay đổi nào mà CAS thông thường bỏ qua?
- [ ] Q085. **[P2]** LongAdder khác AtomicLong thế nào; lựa chọn có thay đổi giữa đếm metric và kiểm soát một trạng thái chính xác không?

## 2.5 Concurrent collections & Producer–consumer

**Chuỗi hỏi:** Q086 → Q087 → Q088 → Q089 → Q090 → Q091 → Q092.

- [ ] Q086. **[P0]** ConcurrentHashMap khác HashMap về bảo đảm truy cập đồng thời như thế nào?
- [ ] Q087. **[P1]** containsKey rồi put trên ConcurrentHashMap có phải một thao tác atomic không; bạn biểu diễn insert-if-absent thế nào?
- [ ] Q088. **[P2]** Nếu value trong ConcurrentHashMap là object mutable, những vấn đề concurrency nào vẫn còn?
- [ ] Q089. **[P2]** CopyOnWriteArrayList phù hợp với workload nào, và mỗi lần ghi phải trả chi phí gì?
- [ ] Q090. **[P1]** BlockingQueue nối producer và consumer như thế nào khi hai bên xử lý với tốc độ khác nhau?
- [ ] Q091. **[P1]** Bounded queue và unbounded queue khác nhau thế nào về overload, latency và nguy cơ hết bộ nhớ?
- [ ] Q092. **[P2]** Nếu giao toàn bộ mutable state cho một owner thread, vấn đề đồng bộ nào giảm đi và vấn đề queueing nào xuất hiện?

## 2.6 Semaphore, condition & Giới hạn tài nguyên

**Chuỗi hỏi:** Q093 → Q094 → Q095 → Q096 → Q097 → Q098.

- [ ] Q093. **[P0]** Semaphore khác mutex thế nào về số lượng bên được vào, quyền sở hữu và mục đích sử dụng?
- [ ] Q094. **[P1]** Semaphore có một permit có hoàn toàn thay thế mutex được không, đặc biệt khi một thread đánh thức thread khác?
- [ ] Q095. **[P1]** Bạn sẽ giới hạn tối đa 10 lời gọi I/O đang chạy trong một process thế nào mà không làm rò rỉ permit khi có lỗi?
- [ ] Q096. **[P2]** Điều gì xảy ra nếu release permit khi chưa acquire thành công hoặc release hai lần?
- [ ] Q097. **[P1]** Cho 10 thread qua semaphore có làm việc cập nhật shared counter của chúng an toàn không?
- [ ] Q098. **[P2]** Chờ condition khác sleep thế nào về việc giữ lock, và vì sao phải kiểm tra lại điều kiện sau khi được đánh thức?

## 2.7 Deadlock, starvation & Livelock

**Chuỗi hỏi:** Q099 → Q100 → Q101 → Q102 → Q103 → Q104.

- [ ] Q099. **[P0]** Deadlock là gì, và bốn điều kiện của deadlock tài nguyên cổ điển là gì?
- [ ] Q100. **[P1]** Hai thread lấy lock A và B theo thứ tự ngược nhau có thể kẹt thế nào; bạn sẽ thay đổi quy tắc lấy lock ra sao?
- [ ] Q101. **[P1]** Bạn dùng thread dump để phân biệt deadlock với lock contention thông thường như thế nào?
- [ ] Q102. **[P1]** Deadlock, starvation và livelock khác nhau thế nào về sự tiến triển của thread và của toàn hệ thống?
- [ ] Q103. **[P2]** Thêm timeout và retry khi lấy lock có thể tránh chờ vô hạn nhưng vẫn tạo livelock như thế nào?
- [ ] Q104. **[P2]** Khi nào fair lock đáng sử dụng, và phải đánh đổi throughput gì so với nonfair lock?

## 2.8 Thread pool, workload & Backpressure

**Chuỗi hỏi:** Q105 → Q106 → Q107 → Q108 → Q109 → Q110 → Q111.

- [ ] Q105. **[P0]** Thread pool giải quyết vấn đề gì, và điều gì xảy ra khi tất cả worker đều bận?
- [ ] Q106. **[P1]** Bạn chọn số worker khác nhau thế nào cho workload CPU-bound và I/O-bound?
- [ ] Q107. **[P1]** Vì sao chỉ biết availableProcessors chưa đủ để xác định kích thước thread pool?
- [ ] Q108. **[P1]** Khi tốc độ nhận task vượt tốc độ xử lý, bạn chọn queue limit và rejection hoặc backpressure policy như thế nào?
- [ ] Q109. **[P1]** Điều gì có thể xảy ra khi mọi worker của một executor đều chờ task khác được submit vào chính executor đó?
- [ ] Q110. **[P1]** Một ứng dụng có hàng nghìn thread, CPU thấp và latency cao; bạn phân biệt chờ I/O, cạn connection pool và lock contention ra sao?
- [ ] Q111. **[P2]** Bạn thiết kế cancellation và orderly shutdown thế nào khi worker đang chạy hoặc bị block?

## 2.9 Platform thread & Virtual thread

**Chuỗi hỏi:** Q112 → Q113 → Q114 → Q115 → Q116 → Q117 → Q118 → Q119 → Q120.

- [ ] Q112. **[P1]** Platform thread được ánh xạ tới OS thread như thế nào, và virtual thread thay đổi mô hình này ra sao?
- [ ] Q113. **[P1]** JDK scheduler và OS scheduler phân chia trách nhiệm như thế nào khi virtual thread chạy trên carrier thread?
- [ ] Q114. **[P1]** Khi virtual thread thực hiện blocking I/O được hỗ trợ, điều gì xảy ra với virtual thread và carrier?
- [ ] Q115. **[P2]** Virtual thread có thể tiếp tục trên carrier khác mà vẫn giữ call stack và danh tính thread như thế nào?
- [ ] Q116. **[P1]** Vì sao virtual threads có thể giúp nhiều request I/O-bound nhưng không làm một phép tính CPU-bound tự nhiên nhanh hơn?
- [ ] Q117. **[P2]** Khi chuyển sang virtual-thread-per-task, những tài nguyên downstream nào vẫn cần giới hạn concurrency?
- [ ] Q118. **[P2]** Vì sao thường không cần pool virtual threads, và state riêng của từng thread có thể gây áp lực RAM thế nào?
- [ ] Q119. **[P3]** Carrier pinning là gì; bạn sẽ kiểm tra khác biệt giữa JDK 21, các JDK mới hơn và native calls như thế nào?
- [ ] Q120. **[P2]** Chuyển giữa hai virtual threads trên cùng carrier có bắt buộc gây OS context switch không?

---

# 3. Networking, HTTP & Chẩn đoán kết nối

Nguồn: [networking/networking.md](Computer%20Science/networking/networking.md).

## 3.1 Vòng đời request & Các tầng mạng

**Chuỗi hỏi:** Q121 → Q122 → Q123 → Q124 → Q125 → Q126 → Q127 → Q128.

- [ ] Q121. **[P0]** Điều gì xảy ra từ lúc nhập một URL HTTPS đến khi nhận được nội dung từ backend?
- [ ] Q122. **[P0]** OSI và TCP/IP phân chia trách nhiệm của HTTP, TCP, IP và Ethernet như thế nào?
- [ ] Q123. **[P1]** Vì sao cần phân tầng mạng, và vì sao không nên coi OSI là bản mô tả chính xác tuyệt đối cho mọi implementation?
- [ ] Q124. **[P1]** Bạn mô tả encapsulation và decapsulation của một request HTTP/2 đi qua TLS và TCP như thế nào?
- [ ] Q125. **[P1]** TCP segment, IP packet và Ethernet frame khác nhau ở đâu?
- [ ] Q126. **[P1]** Một HTTP request có thể nằm trên nhiều TCP segments không; một lần socket read có thể chỉ đọc được một phần message không?
- [ ] Q127. **[P2]** MTU và TCP MSS ảnh hưởng thế nào khi truyền một application message lớn?
- [ ] Q128. **[P1]** Request HTTPS thứ hai có thể bỏ qua những bước thiết lập nào so với request đầu tiên, và trong điều kiện nào?

## 3.2 Địa chỉ, socket & TCP so với UDP

**Chuỗi hỏi:** Q129 → Q130 → Q131 → Q132 → Q133 → Q134 → Q135 → Q136 → Q137.

- [ ] Q129. **[P0]** IP address, port và socket khác nhau như thế nào?
- [ ] Q130. **[P0]** Một server port có thể phục vụ hàng nghìn TCP connections đồng thời bằng cách nào?
- [ ] Q131. **[P1]** Listening socket khác socket nhận được sau accept như thế nào?
- [ ] Q132. **[P1]** IP address và MAC address có vai trò gì khi một packet đi qua router?
- [ ] Q133. **[P1]** Switch và router khác nhau thế nào về thông tin dùng để chuyển tiếp traffic?
- [ ] Q134. **[P0]** TCP và UDP khác nhau về độ tin cậy, thứ tự, ranh giới message và trạng thái kết nối như thế nào?
- [ ] Q135. **[P1]** Khi chọn UDP, ứng dụng hoặc giao thức phía trên phải tự đảm nhận thêm những trách nhiệm gì?
- [ ] Q136. **[P1]** Ứng dụng chạy trên UDP có luôn latency thấp hơn TCP không; bạn cần so sánh theo những điều kiện nào?
- [ ] Q137. **[P2]** Client gửi hai message liên tiếp qua TCP; receiver sẽ xác định ranh giới giữa chúng như thế nào?

## 3.3 TCP handshake, reliability & Điều khiển tốc độ

**Chuỗi hỏi:** Q138 → Q139 → Q140 → Q141 → Q142 → Q143 → Q144 → Q145 → Q146 → Q147.

- [ ] Q138. **[P0]** Bạn mô tả TCP three-way handshake thế nào, và mỗi bên xác nhận được thông tin gì ở từng bước?
- [ ] Q139. **[P0]** Vì sao hai thông điệp không đủ cho quy trình thiết lập TCP thông thường?
- [ ] Q140. **[P1]** Sequence number và acknowledgment number trong TCP biểu diễn điều gì?
- [ ] Q141. **[P0]** TCP bảo đảm dữ liệu đáng tin cậy và đúng thứ tự như thế nào khi packet bị mất, lặp hoặc đảo thứ tự?
- [ ] Q142. **[P1]** Nếu receiver nhận các byte phía sau trước đoạn dữ liệu bị thiếu, ứng dụng có được đọc ngay các byte đó không?
- [ ] Q143. **[P1]** Retransmission timeout và fast retransmit giúp phát hiện và phục hồi mất dữ liệu khác nhau thế nào?
- [ ] Q144. **[P0]** Flow control và congestion control bảo vệ hai đối tượng khác nhau nào?
- [ ] Q145. **[P1]** Receive window và congestion window cùng giới hạn lượng dữ liệu đang truyền như thế nào?
- [ ] Q146. **[P1]** Vì sao TCP cần slow start, và sender thay đổi hành vi ra sao khi phát hiện congestion?
- [ ] Q147. **[P1]** TCP ACK cho biết gì và không cho biết gì về việc server đã xử lý thành công nghiệp vụ?

## 3.4 Đóng kết nối & Giới hạn socket

**Chuỗi hỏi:** Q148 → Q149 → Q150 → Q151 → Q152 → Q153.

- [ ] Q148. **[P1]** TCP đóng kết nối bình thường như thế nào, và vì sao hai chiều có thể đóng ở hai thời điểm khác nhau?
- [ ] Q149. **[P1]** TCP reset khác graceful close ra sao, và HTTP client có thể quan sát thấy lỗi gì?
- [ ] Q150. **[P1]** TIME_WAIT tồn tại để làm gì, và endpoint nào thường đi vào trạng thái đó?
- [ ] Q151. **[P2]** Có 100.000 socket TIME_WAIT trên host; bạn dựa vào đâu để kết luận đây là vấn đề hay hành vi bình thường?
- [ ] Q152. **[P1]** Số socket CLOSE_WAIT tồn tại lâu tăng dần gợi ra điều gì, và bạn sẽ điều tra từ đâu?
- [ ] Q153. **[P2]** Tạo outbound connections liên tục có thể làm cạn ephemeral ports hoặc tài nguyên socket như thế nào?

## 3.5 HTTP semantics, phiên bản & Tái sử dụng kết nối

**Chuỗi hỏi:** Q154 → Q155 → Q156 → Q157 → Q158 → Q159 → Q160 → Q161 → Q162 → Q163 → Q164 → Q165 → Q166 → Q167.

- [ ] Q154. **[P0]** HTTP method safe khác idempotent thế nào; điều đó có ý nghĩa gì với GET, POST, PUT, PATCH và DELETE?
- [ ] Q155. **[P1]** HTTP stateless có mâu thuẫn với persistent connection hoặc session của ứng dụng không?
- [ ] Q156. **[P0]** HTTP keep-alive giúp latency và throughput của HTTPS client như thế nào?
- [ ] Q157. **[P1]** Connection pooling khác việc tạo client và kết nối mới cho từng request thế nào?
- [ ] Q158. **[P1]** HTTP/1.1 pipelining khác HTTP/2 multiplexing ra sao?
- [ ] Q159. **[P1]** Frames và streams trong HTTP/2 cho phép nhiều request dùng chung một connection như thế nào?
- [ ] Q160. **[P1]** Vì sao mất packet ở TCP có thể làm nhiều HTTP/2 streams cùng bị chậm?
- [ ] Q161. **[P2]** HTTP/3 dùng QUIC thay đổi vấn đề head-of-line blocking như thế nào, và những ảnh hưởng nào của mất packet vẫn còn?
- [ ] Q162. **[P2]** QUIC chạy trên UDP thì độ tin cậy, congestion control và bảo mật được thực hiện ở đâu?
- [ ] Q163. **[P3]** HPACK và QPACK giải quyết bài toán gì; vì sao HTTP/3 cần cơ chế nén header phù hợp với các stream độc lập?
- [ ] Q164. **[P2]** Cách tính connection pool capacity thay đổi thế nào giữa HTTP/1.1 và HTTP/2?
- [ ] Q165. **[P2]** Một pooled connection lỗi ngay lần tái sử dụng đầu sau khi idle; bạn kiểm tra timeout và vòng đời connection ở những tầng nào?
- [ ] Q166. **[P3]** QUIC connection ID hỗ trợ client chuyển từ Wi-Fi sang mạng di động khác TCP truyền thống như thế nào?
- [ ] Q167. **[P1]** Cache-Control và conditional request dùng ETag thay đổi việc kiểm tra độ mới và truyền response như thế nào?

## 3.6 Proxy, load balancer & Health check

**Chuỗi hỏi:** Q168 → Q169 → Q170 → Q171 → Q172 → Q173 → Q174 → Q175 → Q176.

- [ ] Q168. **[P0]** Forward proxy và reverse proxy khác nhau về đối tượng mà chúng đại diện như thế nào?
- [ ] Q169. **[P0]** Reverse proxy, load balancer và API gateway có những trách nhiệm nào trùng nhau và khác nhau?
- [ ] Q170. **[P0]** Load balancing L4 khác L7 thế nào về thông tin dùng để định tuyến?
- [ ] Q171. **[P1]** Khi nào round robin, least connections hoặc weighted balancing phù hợp; workload nào có thể khiến mỗi cách chia tải kém?
- [ ] Q172. **[P2]** Consistent hashing giải quyết vấn đề gì khi backend được thêm hoặc bớt, và có thể đánh đổi gì về độ đều của tải?
- [ ] Q173. **[P1]** Active health check và passive failure detection khác nhau thế nào; vì sao backend lỗi vẫn có thể nhận traffic một thời gian?
- [ ] Q174. **[P2]** Nếu health checks liên tục đổi trạng thái hoặc loại nhầm instance khỏe, hệ thống có thể suy giảm dây chuyền ra sao?
- [ ] Q175. **[P2]** Vì sao số lượng và thời gian sống của client connections tới proxy không nhất thiết bằng upstream connections tới backend?
- [ ] Q176. **[P2]** Backend được phép tin forwarded client IP trong những điều kiện nào?

## 3.7 Timeout, retry & Bằng chứng chẩn đoán

**Chuỗi hỏi:** Q177 → Q178 → Q179 → Q180 → Q181 → Q182 → Q183 → Q184 → Q185 → Q186 → Q187 → Q188 → Q189 → Q190.

- [ ] Q177. **[P0]** Bạn phân biệt DNS failure, connection refused, connect timeout, TLS failure và response-read timeout như thế nào?
- [ ] Q178. **[P1]** Timeout chờ connection trong pool khác TCP connect timeout và deadline toàn request ra sao?
- [ ] Q179. **[P1]** Một response vẫn liên tục gửi dữ liệu có thể vượt tổng time budget dự kiến như thế nào?
- [ ] Q180. **[P0]** Từ góc nhìn gateway, HTTP 502, 503 và 504 khác nhau ra sao; cần bằng chứng gì để tìm nguyên nhân?
- [ ] Q181. **[P1]** Một API mất hai giây; bạn tách thời gian DNS, TCP, TLS, chờ byte đầu và tải response như thế nào?
- [ ] Q182. **[P1]** Packet capture có nhiều SYN nhưng không có SYN-ACK; bạn sẽ khoanh vùng lỗi ở client, đường mạng và server ra sao?
- [ ] Q183. **[P1]** TCP connect thành công nhưng TLS thất bại trước khi có HTTP status; bạn kiểm tra những gì?
- [ ] Q184. **[P1]** Retry ở nhiều tầng có thể khuếch đại tải lên một dependency đang lỗi như thế nào?
- [ ] Q185. **[P1]** Bạn phân bổ deadline, số lần retry và backoff thế nào cho một chuỗi lời gọi dịch vụ?
- [ ] Q186. **[P1]** Circuit breaker khác timeout và retry về mục đích và trạng thái hoạt động như thế nào?
- [ ] Q187. **[P2]** Client đã timeout thì công việc phía server có chắc đã dừng chưa, và điều này ảnh hưởng retry như thế nào?
- [ ] Q188. **[P1]** Bạn kết hợp curl, socket states, packet capture, proxy timings và application logs để điều tra lỗi gián đoạn như thế nào?
- [ ] Q189. **[P2]** Ping và traceroute thành công hoặc thất bại có thể chứng minh gì và chưa thể chứng minh gì về một HTTPS API?
- [ ] Q190. **[P2]** Không giải mã HTTPS thì packet capture vẫn cung cấp được những bằng chứng hữu ích nào?

---

# 4. DNS & Service Discovery

Nguồn: [networking/DNS/tutor.md](Computer%20Science/networking/DNS/tutor.md); [networking/networking.md](Computer%20Science/networking/networking.md).

## 4.1 Phân cấp, resolver & Delegation

**Chuỗi hỏi:** Q191 → Q192 → Q193 → Q194 → Q195 → Q196 → Q197 → Q198 → Q199 → Q200 → Q201 → Q202.

- [ ] Q191. **[P0]** DNS giải quyết bài toán gì, và vì sao mô tả nó chỉ là bảng hostname sang IP là chưa đủ?
- [ ] Q192. **[P0]** Bạn mô tả một lần DNS lookup chưa có cache qua stub resolver, recursive resolver, root, TLD và authoritative server như thế nào?
- [ ] Q193. **[P0]** Recursive query khác iterative query ra sao; thành phần nào tiếp tục đi hỏi server khác?
- [ ] Q194. **[P0]** A, AAAA và CNAME khác nhau như thế nào, và khi nào dùng từng loại?
- [ ] Q195. **[P1]** CNAME có thay đổi URL trên trình duyệt hoặc thay thế HTTP redirect không?
- [ ] Q196. **[P1]** NS records và delegation cho phép các tổ chức quản lý những phần khác nhau của namespace như thế nào?
- [ ] Q197. **[P2]** Domain khác zone ra sao khi một subdomain được giao cho authoritative servers khác?
- [ ] Q198. **[P2]** Nếu example.com dùng ns1.example.com làm nameserver, resolver tránh vòng lặp tìm địa chỉ nameserver bằng cách nào?
- [ ] Q199. **[P2]** MX, TXT và PTR giải quyết những nhu cầu nào ngoài việc tìm IP của web server?
- [ ] Q200. **[P2]** SRV cung cấp thêm thông tin gì so với A hoặc AAAA?
- [ ] Q201. **[P3]** SOA đóng vai trò gì khi đồng bộ zone, và AXFR khác IXFR như thế nào?
- [ ] Q202. **[P3]** Vì sao CNAME thông thường có vấn đề tại zone apex; cần kiểm tra gì với alias hoặc flattening của nhà cung cấp DNS?

## 4.2 Cache, TTL & Chuyển endpoint

**Chuỗi hỏi:** Q203 → Q204 → Q205 → Q206 → Q207 → Q208 → Q209 → Q210.

- [ ] Q203. **[P0]** DNS response có thể được cache ở những tầng nào, và TTL kiểm soát điều gì?
- [ ] Q204. **[P1]** Bạn chọn TTL thế nào khi cân bằng latency, tải authoritative server và nhu cầu thay đổi endpoint?
- [ ] Q205. **[P1]** Vì sao sau khi cập nhật DNS, hai client vẫn có thể dùng hai IP khác nhau?
- [ ] Q206. **[P1]** Bạn lên kế hoạch chuyển server bằng DNS thế nào để client còn cache cũ vẫn được phục vụ?
- [ ] Q207. **[P2]** Vì sao giảm TTL cùng lúc đổi IP có thể không giúp các client đang giữ bản ghi cũ chuyển nhanh hơn?
- [ ] Q208. **[P1]** Một hostname từng bị truy vấn trước khi tạo record; vì sao sau khi tạo, một số client vẫn chưa resolve được?
- [ ] Q209. **[P2]** DNS đã trỏ endpoint mới nhưng HTTP client vẫn gọi endpoint cũ; những lớp cache hoặc connection state nào có thể liên quan?
- [ ] Q210. **[P2]** Nếu toàn bộ authoritative servers của zone không truy cập được, client có cache và client cache miss bị ảnh hưởng khác nhau thế nào?

## 4.3 DNS routing, CDN & Discovery

**Chuỗi hỏi:** Q211 → Q212 → Q213 → Q214 → Q215.

- [ ] Q211. **[P1]** DNS load balancing khác cân bằng từng connection hoặc từng HTTP request thế nào; nhiều A records có bảo đảm chia tải đều không?
- [ ] Q212. **[P2]** Anycast DNS khác Geo DNS ở quyết định định tuyến như thế nào?
- [ ] Q213. **[P2]** DNS có thể tham gia chọn CDN edge như thế nào, và vị trí resolver ảnh hưởng kết quả ra sao?
- [ ] Q214. **[P2]** Split-horizon DNS và resolver search domains có thể giải thích việc cùng tên service chỉ hoạt động trong một mạng như thế nào?
- [ ] Q215. **[P2]** Trong một hệ thống Kubernetes giả định, gọi qua Service DNS khác gọi thẳng Pod IP hoặc dùng service registry như thế nào?

## 4.4 Transport, bảo mật & Debug DNS

**Chuỗi hỏi:** Q216 → Q217 → Q218 → Q219 → Q220 → Q221 → Q222 → Q223 → Q224 → Q225 → Q226.

- [ ] Q216. **[P1]** Vì sao DNS thường dùng UDP, và resolver xử lý mất query hoặc response như thế nào?
- [ ] Q217. **[P1]** Khi nào DNS cần TCP; firewall chỉ cho UDP port 53 đi qua có thể tạo lỗi gì?
- [ ] Q218. **[P3]** EDNS ảnh hưởng kích thước response thế nào, và UDP response lớn tạo thêm rủi ro gì trên đường mạng?
- [ ] Q219. **[P1]** Hướng điều tra NXDOMAIN, SERVFAIL và DNS timeout khác nhau như thế nào?
- [ ] Q220. **[P1]** dig resolve được hostname nhưng Java client báo UnknownHostException; bạn sẽ so sánh hai đường lookup ở đâu?
- [ ] Q221. **[P2]** Hỏi recursive resolver, hỏi authoritative server trực tiếp và dùng dig +trace cung cấp những loại bằng chứng khác nhau nào?
- [ ] Q222. **[P2]** DNS cache poisoning có thể ảnh hưởng nhiều client cùng lúc như thế nào?
- [ ] Q223. **[P2]** DNSSEC khác DNS over HTTPS và DNS over TLS về điều được bảo vệ như thế nào?
- [ ] Q224. **[P3]** DNSSEC thiết lập chuỗi tin cậy qua các zone được delegate ra sao; validation failure có thể xuất hiện dưới dạng lỗi gì?
- [ ] Q225. **[P3]** Open DNS resolver có thể bị lợi dụng để reflection hoặc amplification như thế nào?
- [ ] Q226. **[P3]** CAA cho domain owner kiểm soát điều gì về cấp certificate, và khác DNSSEC ở đâu?

---

# 5. TLS, HTTPS & Ranh giới tin cậy

Nguồn: [networking/TSL/terminate TSL design deep dive.md](Computer%20Science/networking/TSL/terminate%20TSL%20design%20deep%20dive.md); [networking/TSL/tsl.md](Computer%20Science/networking/TSL/tsl.md).

## 5.1 Certificate, handshake & Mã hóa

**Chuỗi hỏi:** Q227 → Q228 → Q229 → Q230 → Q231 → Q232 → Q233 → Q234 → Q235 → Q236 → Q237 → Q238 → Q239.

- [ ] Q227. **[P0]** HTTPS bổ sung những tính chất bảo mật nào cho HTTP, và vì sao chỉ mã hóa chưa đủ chống giả mạo server?
- [ ] Q228. **[P0]** Certificate chứa gì; CA và trust store giúp client xác thực server như thế nào?
- [ ] Q229. **[P0]** TCP handshake và TLS handshake khác nhau về mục đích ra sao?
- [ ] Q230. **[P1]** Bạn mô tả một TLS 1.3 handshake xác thực bằng certificate ở mức khái quát như thế nào?
- [ ] Q231. **[P1]** Client cần kiểm tra gì trước khi chấp nhận certificate cho hostname đang truy cập?
- [ ] Q232. **[P1]** Vì sao sao chép public certificate chưa đủ để giả mạo server trong TLS handshake?
- [ ] Q233. **[P2]** Kiểm tra certificate chain, CertificateVerify và Finished đem lại những bảo đảm khác nhau nào?
- [ ] Q234. **[P0]** Vì sao TLS kết hợp asymmetric cryptography và symmetric cryptography; mỗi loại làm nhiệm vụ gì?
- [ ] Q235. **[P1]** Hai phía thiết lập shared secret như thế nào mà không gửi trực tiếp traffic key cuối cùng qua mạng?
- [ ] Q236. **[P2]** Forward secrecy thay đổi hậu quả của việc private key server bị lộ sau này như thế nào?
- [ ] Q237. **[P1]** SNI và ALPN có vai trò khác nhau gì trên một HTTPS endpoint?
- [ ] Q238. **[P2]** TLS session resumption khác tái sử dụng một TLS connection đang mở như thế nào?
- [ ] Q239. **[P2]** TLS 1.3 0-RTT có rủi ro gì với request gây side effect, và bạn sẽ đánh giá operation nào được phép sử dụng?

## 5.2 Termination, re-encryption & Passthrough

**Chuỗi hỏi:** Q240 → Q241 → Q242 → Q243 → Q244 → Q245 → Q246.

- [ ] Q240. **[P0]** Terminate TLS tại load balancer nghĩa là gì, và public client đang xác thực thành phần nào?
- [ ] Q241. **[P1]** TLS termination với HTTP backend, termination rồi re-encryption và TLS passthrough khác nhau ra sao?
- [ ] Q242. **[P1]** Khi proxy giải mã rồi mã hóa lại, các connections và khóa có độc lập không; proxy có đọc được HTTP payload không?
- [ ] Q243. **[P1]** Bạn chọn terminate TLS ở gateway thay vì passthrough tới ứng dụng dựa trên những trade-off nào?
- [ ] Q244. **[P2]** Gateway passthrough có thể định tuyến theo thông tin gì và không thể kiểm tra những gì bên trong HTTP?
- [ ] Q245. **[P2]** Mọi hop đều dùng HTTPS đã đủ để kết luận toàn tuyến an toàn chưa; cần xác minh peer identity và quyền truy cập plaintext ở đâu?
- [ ] Q246. **[P2]** Client bắt tay TLS với edge thành công nhưng edge bắt tay với backend thất bại; bạn phân biệt hai lỗi bằng dữ liệu nào?

## 5.3 mTLS, certificate lifecycle & Trust boundary

**Chuỗi hỏi:** Q247 → Q248 → Q249 → Q250 → Q251.

- [ ] Q247. **[P1]** mTLS khác TLS chỉ xác thực server như thế nào, và khi nào phù hợp cho service-to-service communication?
- [ ] Q248. **[P2]** mTLS xác định caller là một service có đồng nghĩa caller được phép thực hiện mọi operation không?
- [ ] Q249. **[P3]** Public CA, internal CA và service-mesh proxy quản lý certificate đem lại những trade-off nào?
- [ ] Q250. **[P2]** Bạn rotate certificate hoặc trust root trên nhiều hop thế nào khi các client không cập nhật đồng thời?
- [ ] Q251. **[P2]** Nếu gateway không xác minh đúng certificate của upstream, re-encryption còn thiếu bảo đảm nào?

---

# 6. Database & PostgreSQL

Nguồn: [Database/b-tree.md](Computer%20Science/Database/b-tree.md); [Database/database.md](Computer%20Science/Database/database.md).

## 6.1 Mô hình dữ liệu & Constraints

**Chuỗi hỏi:** Q252 → Q253 → Q254 → Q255 → Q256 → Q257 → Q258 → Q259.

- [ ] Q252. **[P0]** Bạn chọn relational database hay document/key-value database dựa trên data model, access patterns và transaction requirements như thế nào?
- [ ] Q253. **[P0]** Primary key, foreign key, unique constraint và check constraint bảo vệ những khía cạnh khác nhau nào của dữ liệu?
- [ ] Q254. **[P0]** Normalization giải quyết vấn đề gì; bạn minh họa update anomaly bằng dữ liệu customer và order như thế nào?
- [ ] Q255. **[P1]** Khi nào chủ động denormalize, và bạn giữ các bản dữ liệu trùng lặp nhất quán bằng cách nào?
- [ ] Q256. **[P1]** Primary key khác unique constraint về danh tính row và business uniqueness như thế nào?
- [ ] Q257. **[P1]** Composite unique constraint biểu diễn invariant giữa nhiều cột khác gì đặt unique riêng cho từng cột?
- [ ] Q258. **[P1]** Application validation có vai trò gì nếu database đã có constraint?
- [ ] Q259. **[P2]** Khi nào nên giữ snapshot lịch sử trên order thay vì luôn đọc giá trị hiện tại từ bảng tham chiếu?

## 6.2 Index, B-tree & Chi phí truy vấn

**Chuỗi hỏi:** Q260 → Q261 → Q262 → Q263 → Q264 → Q265 → Q266 → Q267 → Q268.

- [ ] Q260. **[P0]** Index là gì, và nó thay đổi cách tìm vài rows trong một bảng rất lớn như thế nào?
- [ ] Q261. **[P0]** Bạn mô tả đường đi từ root của B-tree đến row cần tìm ra sao?
- [ ] Q262. **[P1]** Vì sao database thường chọn B-tree có fan-out lớn thay vì binary search tree?
- [ ] Q263. **[P1]** B-tree và B+tree khác nhau về vị trí lưu entries và cách range scan như thế nào?
- [ ] Q264. **[P1]** B-tree index khác hash index với equality, range và ORDER BY ra sao?
- [ ] Q265. **[P0]** Vì sao không nên tạo index cho mọi cột?
- [ ] Q266. **[P1]** Selectivity ảnh hưởng việc chọn index scan hay sequential scan như thế nào?
- [ ] Q267. **[P2]** Vì sao tree có bốn level không có nghĩa mỗi lookup luôn cần bốn lần đọc ổ đĩa?
- [ ] Q268. **[P2]** PostgreSQL index tìm row qua heap TID khác InnoDB secondary index tìm row qua primary key như thế nào?

## 6.3 Composite index & Query plan

**Chuỗi hỏi:** Q269 → Q270 → Q271 → Q272 → Q273 → Q274 → Q275 → Q276 → Q277 → Q278.

- [ ] Q269. **[P0]** Với query lọc orders theo user_id và sắp xếp created_at, bạn so sánh full scan, hai index riêng và composite index thế nào?
- [ ] Q270. **[P0]** Entries trong composite index được sắp xếp ra sao, và vì sao thứ tự cột quan trọng?
- [ ] Q271. **[P1]** Index (user_id, created_at) hỗ trợ khác nhau thế nào khi query chỉ lọc user_id, lọc cả hai hoặc chỉ lọc created_at?
- [ ] Q272. **[P1]** Equality và range predicates ảnh hưởng lựa chọn thứ tự cột trong index như thế nào?
- [ ] Q273. **[P1]** Khi nào index đáp ứng được ORDER BY và dừng sớm cho LIMIT mà không cần sort thêm?
- [ ] Q274. **[P1]** Bạn dùng EXPLAIN và EXPLAIN ANALYZE để kiểm tra access path và sai lệch ước lượng số rows như thế nào?
- [ ] Q275. **[P2]** Vì sao cùng SQL và index nhưng query plan có thể đổi khi dữ liệu lớn lên hoặc phân bố giá trị thay đổi?
- [ ] Q276. **[P2]** Covering index đem lại lợi ích gì và phải trả thêm chi phí storage, cache và writes nào?
- [ ] Q277. **[P2]** Vì sao PostgreSQL index-only scan vẫn có thể cần đọc heap dù index đã chứa mọi cột được yêu cầu?
- [ ] Q278. **[P3]** Skip scan có thể ảnh hưởng nguyên tắc leftmost prefix trong trường hợp nào, và cần xác minh gì theo phiên bản database?

## 6.4 Transaction, ACID & Isolation

**Chuỗi hỏi:** Q279 → Q280 → Q281 → Q282 → Q283 → Q284 → Q285 → Q286 → Q287 → Q288 → Q289.

- [ ] Q279. **[P0]** Transaction là gì; bạn giải thích từng tính chất ACID bằng một thao tác chuyển tiền giữa hai tài khoản như thế nào?
- [ ] Q280. **[P0]** Bạn xác định transaction boundary ra sao, và vì sao một HTTP request không nhất thiết tương ứng một transaction?
- [ ] Q281. **[P0]** Dirty read, non-repeatable read và phantom read khác nhau thế nào qua ví dụ hai transactions chạy đồng thời?
- [ ] Q282. **[P1]** Bạn chọn isolation level theo yêu cầu nào, và vì sao cần kiểm tra hành vi riêng của database engine?
- [ ] Q283. **[P1]** MVCC cho phép readers và writers làm việc đồng thời như thế nào?
- [ ] Q284. **[P1]** Snapshot của PostgreSQL Read Committed khác Repeatable Read ra sao?
- [ ] Q285. **[P2]** Hai transactions đều đọc snapshot hợp lệ vẫn có thể cùng phá vỡ một business invariant như thế nào?
- [ ] Q286. **[P1]** Serializable bảo đảm điều gì, và ứng dụng phải xử lý serialization failure ra sao?
- [ ] Q287. **[P1]** WAL giúp khôi phục dữ liệu đã commit sau crash như thế nào?
- [ ] Q288. **[P2]** Commit, flush data pages và replicate sang node khác là những mốc khác nhau nào?
- [ ] Q289. **[P2]** Vì sao transaction kéo dài có thể làm MVCC database tăng storage và giảm hiệu năng?

## 6.5 Concurrent writes, locking & Deadlock

**Chuỗi hỏi:** Q290 → Q291 → Q292 → Q293 → Q294 → Q295 → Q296 → Q297 → Q298 → Q299.

- [ ] Q290. **[P0]** Hai requests cùng kiểm tra dữ liệu chưa tồn tại rồi insert; vì sao application check không đủ ngăn duplicate?
- [ ] Q291. **[P1]** Database xử lý cạnh tranh để bảo vệ unique constraint khác application check-then-insert như thế nào?
- [ ] Q292. **[P1]** Khi unique violation xảy ra trong PostgreSQL transaction, bạn phải xét trạng thái transaction nào trước khi tiếp tục query?
- [ ] Q293. **[P1]** Khi nào upsert hợp lý, và khi nào cập nhật row đang có lại che giấu một lỗi nghiệp vụ?
- [ ] Q294. **[P0]** Hai người cùng mua sản phẩm chỉ còn một đơn vị; bạn có thể đề xuất những cách nào để tránh overselling?
- [ ] Q295. **[P1]** Bạn so sánh atomic conditional UPDATE, SELECT FOR UPDATE và version-column check cho tình huống đó như thế nào?
- [ ] Q296. **[P1]** Optimistic update trả affected rows bằng 0 nghĩa là gì, và ứng dụng nên retry hay báo conflict trong trường hợp nào?
- [ ] Q297. **[P1]** Shared lock và exclusive lock tương thích ra sao; mô hình đó cần được hiểu thế nào trong database dùng MVCC?
- [ ] Q298. **[P1]** Hai transactions khóa hai tài khoản theo thứ tự ngược nhau tạo deadlock ra sao, và database xử lý deadlock victim thế nào?
- [ ] Q299. **[P2]** Thứ tự truy cập rows, độ dài transaction và query efficiency ảnh hưởng lock contention như thế nào?

## 6.6 Pagination, joins & Batch processing

**Chuỗi hỏi:** Q300 → Q301 → Q302 → Q303 → Q304 → Q305 → Q306 → Q307.

- [ ] Q300. **[P0]** Offset và cursor pagination khác nhau thế nào về hiệu năng và khả năng chuyển trang?
- [ ] Q301. **[P1]** Vì sao OFFSET lớn vẫn có thể chậm dù ORDER BY đã có index?
- [ ] Q302. **[P1]** Nếu nhiều rows có cùng created_at, bạn thiết kế cursor thế nào để tiếp tục không mơ hồ?
- [ ] Q303. **[P1]** Index nào phù hợp để lấy trang tiếp theo của một user's orders theo created_at và id?
- [ ] Q304. **[P1]** Insert, delete hoặc cập nhật sort key giữa hai lần lấy trang có thể làm kết quả thay đổi thế nào?
- [ ] Q305. **[P2]** Cursor pagination có tự bảo đảm một snapshot nhất quán cho toàn bộ export khi dữ liệu đang đổi không?
- [ ] Q306. **[P1]** INNER JOIN và LEFT JOIN khác nhau thế nào khi cần tìm các records không có bên đối ứng?
- [ ] Q307. **[P2]** Bạn chọn batch size thế nào khi cân bằng số round trips, RAM, lock duration và khả năng phục hồi lỗi?

## 6.7 B-tree mutation & Storage internals

**Chuỗi hỏi:** Q308 → Q309 → Q310 → Q311 → Q312 → Q313 → Q314 → Q315 → Q316 → Q317 → Q318.

- [ ] Q308. **[P2]** Khi leaf đầy, B-tree split thế nào và việc split có thể lan lên parent hoặc root ra sao?
- [ ] Q309. **[P2]** Key width và fill factor ảnh hưởng tree size, cache efficiency và tần suất split như thế nào?
- [ ] Q310. **[P2]** Sequential IDs và random UUIDs tạo khác biệt gì về locality, page writes và contention?
- [ ] Q311. **[P2]** Vì sao DELETE một row không nhất thiết xóa ngay physical index entry trong PostgreSQL?
- [ ] Q312. **[P3]** Slotted-page layout giữ logical key order thế nào khi variable-length records nằm ở các offsets khác nhau?
- [ ] Q313. **[P3]** Index biểu diễn nhiều duplicate keys thế nào mà việc điều hướng vật lý vẫn không mơ hồ?
- [ ] Q314. **[P3]** Transaction lock khác page latch về đối tượng bảo vệ và thời gian giữ như thế nào?
- [ ] Q315. **[P3]** Reader tìm đúng key ra sao khi child vừa split nhưng parent chưa cập nhật xong?
- [ ] Q316. **[P3]** Recovery cần bảo toàn điều gì nếu crash xảy ra giữa một page split sửa nhiều pages?
- [ ] Q317. **[P3]** Rút ngắn separator keys giúp tăng fan-out thế nào mà vẫn định tuyến đúng?
- [ ] Q318. **[P3]** B-tree và LSM-tree đánh đổi write path, read amplification và background maintenance ra sao?

---

# 7. Redis, Caching & Distributed Lock

Nguồn: [Redis/tutor.md](Computer%20Science/Redis/tutor.md).

## 7.1 Execution model & Data structures

**Chuỗi hỏi:** Q319 → Q320 → Q321 → Q322 → Q323 → Q324 → Q325 → Q326.

- [ ] Q319. **[P0]** Redis khác một key-value map trong process và một relational database về vai trò như thế nào?
- [ ] Q320. **[P0]** Vì sao Redis có latency thấp, và những yếu tố nào có thể làm lợi thế đó biến mất?
- [ ] Q321. **[P1]** Redis phục vụ nhiều clients ra sao khi phần lớn command execution diễn ra tuần tự?
- [ ] Q322. **[P0]** INCR khác client tự GET, cộng một rồi SET về atomicity như thế nào?
- [ ] Q323. **[P1]** Bạn chọn serialized JSON hay Redis Hash để lưu một object dựa trên access pattern nào?
- [ ] Q324. **[P1]** List, Set và Sorted Set phù hợp khác nhau thế nào với queue, membership và ranking?
- [ ] Q325. **[P2]** Thay đổi kích thước collection có thể làm internal encoding và memory footprint thay đổi ra sao?
- [ ] Q326. **[P3]** Khi nào Bitmap hoặc HyperLogLog phù hợp hơn lưu đầy đủ các phần tử, và phải đánh đổi độ chính xác hay phạm vi sử dụng gì?

## 7.2 Cache patterns & Invalidation

**Chuỗi hỏi:** Q327 → Q328 → Q329 → Q330 → Q331 → Q332 → Q333 → Q334 → Q335.

- [ ] Q327. **[P0]** Bạn mô tả cache-aside khi cache hit, cache miss và khi ghi vào database như thế nào?
- [ ] Q328. **[P1]** Cache-aside, write-through và write-behind khác nhau thế nào về latency, consistency và rủi ro mất dữ liệu?
- [ ] Q329. **[P0]** Bạn chọn TTL cho cache dựa trên freshness, hit rate và database load như thế nào?
- [ ] Q330. **[P1]** Sau khi cập nhật database, bạn so sánh xóa cache với cập nhật trực tiếp giá trị cache ra sao?
- [ ] Q331. **[P1]** Database commit thành công nhưng invalidate cache thất bại thì request sau có thể nhìn thấy gì?
- [ ] Q332. **[P2]** Reader đọc giá trị cũ, writer commit rồi xóa cache, sau đó reader ghi lại cache; vấn đề gì xảy ra?
- [ ] Q333. **[P1]** Khi nào chọn local cache, shared Redis cache hoặc kết hợp cả hai?
- [ ] Q334. **[P2]** Thêm L1 cache trên từng instance tạo thêm những vấn đề invalidation và capacity nào?
- [ ] Q335. **[P1]** Redis unavailable thì bạn quyết định bypass cache, trả dữ liệu cũ hay từ chối request dựa trên điều gì?

## 7.3 TTL, eviction & Cache overload

**Chuỗi hỏi:** Q336 → Q337 → Q338 → Q339 → Q340 → Q341 → Q342 → Q343 → Q344 → Q345.

- [ ] Q336. **[P0]** Expiration khác eviction do memory pressure như thế nào?
- [ ] Q337. **[P1]** Redis thu hồi expired keys không còn được client đọc bằng cách nào?
- [ ] Q338. **[P1]** LRU, LFU và noeviction phù hợp với những workload khác nhau nào?
- [ ] Q339. **[P1]** Một hot key hết hạn làm hàng nghìn requests cùng cache miss; hệ thống sẽ chịu tác động gì?
- [ ] Q340. **[P1]** Bạn so sánh request coalescing, rebuild lock, refresh-ahead và stale-while-revalidate để xử lý tình huống đó như thế nào?
- [ ] Q341. **[P2]** Nhiều keys hết TTL cùng lúc khác một hot key hết hạn thế nào, và TTL jitter tác động vào vấn đề nào?
- [ ] Q342. **[P1]** Requests liên tục tới ID không tồn tại gây cache penetration ra sao; negative caching hoặc Bloom filter có trade-off gì?
- [ ] Q343. **[P1]** Hot key khác big key như thế nào khi điều tra Redis latency?
- [ ] Q344. **[P2]** Vì sao thêm Redis shards có thể không giảm tải của một key cực kỳ phổ biến?
- [ ] Q345. **[P2]** Ngoài payload của keys, bạn cần dự phòng RAM cho những thành phần nào khi đặt maxmemory?

## 7.4 Pipeline, MULTI/EXEC, WATCH & Lua

**Chuỗi hỏi:** Q346 → Q347 → Q348 → Q349 → Q350 → Q351 → Q352.

- [ ] Q346. **[P1]** Pipelining giảm chi phí nào, và vì sao không thể coi pipeline là transaction?
- [ ] Q347. **[P2]** Pipeline batch quá lớn có thể ảnh hưởng buffer memory và latency như thế nào?
- [ ] Q348. **[P1]** MULTI/EXEC bảo đảm gì về thứ tự thực thi, và khác PostgreSQL transaction ở đâu?
- [ ] Q349. **[P1]** Một command lỗi lúc EXEC thì những commands khác trong transaction có thể xảy ra chuyện gì?
- [ ] Q350. **[P2]** WATCH phát hiện conflict trong read-modify-write như thế nào, và client cần xử lý abort ra sao?
- [ ] Q351. **[P1]** Khi nào Lua phù hợp hơn nhiều commands phía client cho một conditional update?
- [ ] Q352. **[P2]** Lua script chạy lâu có thể ảnh hưởng các Redis clients khác như thế nào?

## 7.5 Persistence, replication & Cluster

**Chuỗi hỏi:** Q353 → Q354 → Q355 → Q356 → Q357 → Q358 → Q359 → Q360 → Q361.

- [ ] Q353. **[P1]** RDB và AOF khác nhau thế nào về cách khôi phục, write cost và cửa sổ có thể mất dữ liệu?
- [ ] Q354. **[P2]** Các AOF fsync policies thay đổi độ trễ acknowledgment và durability ra sao?
- [ ] Q355. **[P2]** Vì sao snapshot Redis có dataset lớn và write rate cao có thể làm RAM tăng mạnh?
- [ ] Q356. **[P1]** Redis primary ACK một write rồi crash trước replication thì chuyện gì có thể xảy ra?
- [ ] Q357. **[P1]** Replication, automatic failover và sharding là ba khả năng khác nhau như thế nào?
- [ ] Q358. **[P1]** Khi nào Sentinel là đủ, và khi nào cần Redis Cluster?
- [ ] Q359. **[P2]** Hash slots giúp resharding khác cách hash trực tiếp theo số nodes như thế nào?
- [ ] Q360. **[P2]** Hash tags ảnh hưởng multi-key operations và Lua scripts như thế nào; gom keys cùng slot có thể tạo bất lợi gì?
- [ ] Q361. **[P2]** Trong failover, client phải xử lý topology, reconnect và consistency như thế nào?

## 7.6 Distributed lock & Lease failure

**Chuỗi hỏi:** Q362 → Q363 → Q364 → Q365 → Q366 → Q367 → Q368 → Q369 → Q370 → Q371.

- [ ] Q362. **[P0]** Distributed lock giải quyết loại tranh chấp nào mà lock trong một process không giải quyết được?
- [ ] Q363. **[P0]** Tách SETNX và EXPIRE thành hai calls có failure window gì nếu worker crash?
- [ ] Q364. **[P1]** Bạn cấp lock có thời hạn và phân biệt owner hiện tại với owner trước đó bằng cách nào?
- [ ] Q365. **[P1]** Vì sao kiểm tra owner và xóa lock phải atomic?
- [ ] Q366. **[P0]** Worker dừng lâu hơn TTL, worker khác lấy lock, rồi worker cũ chạy lại; mutual exclusion có thể bị phá như thế nào?
- [ ] Q367. **[P1]** Gia hạn lease định kỳ vẫn còn những failure cases nào?
- [ ] Q368. **[P1]** Primary failover có thể làm hai clients cùng tin rằng mình giữ lock ra sao?
- [ ] Q369. **[P2]** Fencing token chống stale lock holder như thế nào, và downstream resource phải thực thi điều kiện gì?
- [ ] Q370. **[P2]** Lock để giảm công việc trùng khác lock để bảo vệ correctness như thế nào về mức bảo đảm cần thiết?
- [ ] Q371. **[P3]** Bạn phải kiểm tra những giả định về thời gian và lỗi nào trước khi dùng Redlock cho thao tác đòi hỏi correctness cao?

## 7.7 Rate limiting, session & Redis diagnosis

**Chuỗi hỏi:** Q372 → Q373 → Q374 → Q375 → Q376 → Q377 → Q378 → Q379 → Q380.

- [ ] Q372. **[P1]** Fixed window, sliding window và token bucket khác nhau về việc chấp nhận burst như thế nào?
- [ ] Q373. **[P1]** Limiter tách INCR, EXPIRE và kiểm tra kết quả có những race hoặc crash windows nào?
- [ ] Q374. **[P2]** Trong sorted-set sliding-window limiter, những bước nào cần được phối hợp khi requests đến đồng thời?
- [ ] Q375. **[P2]** Redis lỗi thì chọn fail-open hay fail-closed cho rate limiter sẽ ảnh hưởng hệ thống thế nào?
- [ ] Q376. **[P1]** Session nằm trong Redis khác session chỉ nằm trong RAM một backend instance như thế nào?
- [ ] Q377. **[P1]** KEYS hoặc đọc toàn bộ một collection lớn có thể trì hoãn clients khác ra sao; bạn sẽ duyệt dữ liệu theo cách nào?
- [ ] Q378. **[P2]** Khi dùng SCAN trong lúc keys đang đổi, cần hiểu những bảo đảm nào để cleanup không sai?
- [ ] Q379. **[P2]** Ứng dụng thấy Redis latency cao nhưng SLOWLOG không có gì đáng kể; bạn sẽ kiểm tra thêm ở đâu?
- [ ] Q380. **[P2]** Hit rate, evictions, RSS, replication lag và command latency giúp phân biệt các Redis bottlenecks như thế nào?

## 7.8 Pub/Sub, List queue & Streams

**Chuỗi hỏi:** Q381 → Q382 → Q383 → Q384.

- [ ] Q381. **[P1]** Redis Pub/Sub khác Streams như thế nào khi subscriber offline rồi kết nối lại?
- [ ] Q382. **[P2]** Worker pop job khỏi List rồi crash trước khi hoàn tất; job đó có thể ra sao?
- [ ] Q383. **[P2]** Pending entries và acknowledgments của Stream consumer group hỗ trợ phục hồi consumer failure như thế nào?
- [ ] Q384. **[P2]** Retention, acknowledgment và persistence settings ảnh hưởng khả năng replay một stream event như thế nào?

---

# 8. Kafka & Event-driven Messaging

Nguồn: [Kafka/tutor.md](Computer%20Science/Kafka/tutor.md).

## 8.1 Event log, kiến trúc & Lựa chọn công nghệ

**Chuỗi hỏi:** Q385 → Q386 → Q387 → Q388 → Q389 → Q390.

- [ ] Q385. **[P0]** Kafka hoạt động như một event log khác queue xóa message sau acknowledgment như thế nào?
- [ ] Q386. **[P0]** Producer, broker, topic, partition và consumer phối hợp ra sao trong vòng đời một event?
- [ ] Q387. **[P0]** Một Kafka record được xác định bằng thông tin nào; vì sao offset riêng lẻ chưa đủ trong một topic?
- [ ] Q388. **[P1]** Chuyển từ synchronous request sang event-driven processing làm latency, consistency và cách xử lý lỗi thay đổi thế nào?
- [ ] Q389. **[P2]** Khi nào database-backed job table hoặc RabbitMQ phù hợp hơn Kafka?
- [ ] Q390. **[P2]** Bạn so sánh Redis Streams và Kafka theo retention, replay, scale và chi phí vận hành như thế nào?

## 8.2 Partition, key & Ordering

**Chuỗi hỏi:** Q391 → Q392 → Q393 → Q394 → Q395 → Q396 → Q397.

- [ ] Q391. **[P0]** Kafka bảo đảm thứ tự ở phạm vi nào, và có thể kỳ vọng gì giữa các partitions?
- [ ] Q392. **[P0]** Producer chọn partition như thế nào, và message key ảnh hưởng ordering của events liên quan ra sao?
- [ ] Q393. **[P1]** Bạn chọn partition key theo user, order hoặc conversation dựa trên invariant nào?
- [ ] Q394. **[P1]** Nếu cần strict ordering cho toàn bộ topic, throughput và consumer parallelism bị giới hạn thế nào?
- [ ] Q395. **[P1]** Vì sao thêm consumers không giải quyết được bottleneck khi phần lớn events thuộc một key rất nóng?
- [ ] Q396. **[P2]** Bạn chọn số partitions thế nào khi cân bằng throughput, khả năng tăng trưởng và operational overhead?
- [ ] Q397. **[P2]** Tăng partition count có thể ảnh hưởng per-key ordering và stateful consumers như thế nào?

## 8.3 Consumer groups, assignment & Rebalance

**Chuỗi hỏi:** Q398 → Q399 → Q400 → Q401 → Q402 → Q403 → Q404 → Q405.

- [ ] Q398. **[P0]** Consumers trong cùng group chia partitions thế nào, và hai groups riêng biệt nhận dữ liệu khác nhau ra sao?
- [ ] Q399. **[P0]** Topic có 6 partitions và 10 consumers trong một consumer group thông thường thì có bao nhiêu consumers được xử lý partitions đồng thời?
- [ ] Q400. **[P1]** Pull model của Kafka cho consumer kiểm soát lượng công việc lấy về như thế nào?
- [ ] Q401. **[P1]** Hai ứng dụng đều phải nhận mọi event thì bạn tổ chức groups và instances ra sao?
- [ ] Q402. **[P1]** Khi consumer crash, partition ownership và công việc chưa commit được xử lý như thế nào?
- [ ] Q403. **[P1]** Những sự kiện nào kích hoạt rebalance, và vì sao rebalance liên tục có thể làm lag hoặc duplicate processing tăng?
- [ ] Q404. **[P2]** Handler chạy lâu có thể khiến consumer còn sống bị mất partition assignment như thế nào?
- [ ] Q405. **[P3]** Static membership và cooperative assignment thay đổi mức gián đoạn khi restart hoặc rebalance ra sao?

## 8.4 Offset, delivery guarantees & Transactions

**Chuỗi hỏi:** Q406 → Q407 → Q408 → Q409 → Q410 → Q411 → Q412 → Q413 → Q414.

- [ ] Q406. **[P0]** Current position khác committed offset thế nào; khi consumer restart thì thông tin nào quyết định điểm tiếp tục?
- [ ] Q407. **[P0]** Commit offset trước xử lý khác commit sau xử lý như thế nào khi có crash?
- [ ] Q408. **[P0]** Consumer cập nhật database thành công rồi crash trước offset commit; lần chạy tiếp theo xảy ra gì, và làm sao giữ business effect đúng?
- [ ] Q409. **[P1]** Consumer-group offsets nằm ở đâu, và consumer chuyển máy vẫn tìm lại tiến độ bằng cách nào?
- [ ] Q410. **[P1]** Broker đã lưu record nhưng ACK bị mất; producer retry có thể tạo kết quả gì?
- [ ] Q411. **[P1]** Idempotent producer nhận diện retry ra sao, và không tự loại bỏ được những duplicate business events nào?
- [ ] Q412. **[P1]** Trong consume-process-produce, Kafka transaction cần commit những gì cùng nhau để tránh duplicate output sau crash?
- [ ] Q413. **[P2]** Consumer dùng read_committed nhìn thấy output của aborted hoặc chưa hoàn tất transaction như thế nào?
- [ ] Q414. **[P1]** Kafka exactly-once bảo đảm đến đâu khi consumer gọi một external API hoặc ghi vào database ngoài Kafka?

## 8.5 Replication, ACK & Broker failure

**Chuỗi hỏi:** Q415 → Q416 → Q417 → Q418 → Q419 → Q420 → Q421.

- [ ] Q415. **[P0]** Partition leader, followers và in-sync replicas có vai trò gì khi Kafka nhận write?
- [ ] Q416. **[P0]** acks=0, acks=1 và acks=all khác nhau về điều producer biết khi send hoàn tất như thế nào?
- [ ] Q417. **[P1]** Leader ACK với acks=1 rồi chết trước khi followers sao chép thì có những khả năng nào?
- [ ] Q418. **[P1]** Với replication factor 3, min.insync.replicas 2 và acks=all, hành vi ghi đổi thế nào khi ISR giảm từ 3 xuống 2 rồi 1?
- [ ] Q419. **[P1]** Leader failure làm việc chọn leader mới và cập nhật client metadata diễn ra như thế nào?
- [ ] Q420. **[P2]** Nếu chỉ còn out-of-sync replica, cho phép nó làm leader đánh đổi availability với nguy cơ gì?
- [ ] Q421. **[P3]** Brokers khác KRaft controllers về trách nhiệm như thế nào; quorum 3 controllers còn hoạt động được trong những tình huống lỗi nào?

## 8.6 Throughput, batching & Storage

**Chuỗi hỏi:** Q422 → Q423 → Q424 → Q425.

- [ ] Q422. **[P0]** Kafka lưu dữ liệu trên disk nhưng vẫn đạt throughput cao nhờ những đặc điểm nào?
- [ ] Q423. **[P1]** Điều gì xảy ra từ application send đến khi một batch tới partition leader?
- [ ] Q424. **[P1]** batch.size, linger.ms và compression đánh đổi throughput, latency, RAM và CPU như thế nào?
- [ ] Q425. **[P2]** OS page cache và zero-copy giúp Kafka phục vụ consumers ra sao, và encrypted transport có thể thay đổi lợi ích đó thế nào?

## 8.7 Retention, replay & Event contracts

**Chuỗi hỏi:** Q426 → Q427 → Q428 → Q429 → Q430.

- [ ] Q426. **[P1]** Một consumer replay lịch sử mà không đổi tiến độ của group khác bằng cách nào, và replay bị giới hạn bởi điều gì?
- [ ] Q427. **[P1]** Time/size retention khác log compaction thế nào; bạn chọn gì cho audit history và latest-state changelog?
- [ ] Q428. **[P2]** Vì sao partition log được chia thành segments, và điều này ảnh hưởng lúc xóa dữ liệu vật lý như thế nào?
- [ ] Q429. **[P1]** Bạn thay đổi event schema thế nào khi producers và consumers cũ, mới cùng tồn tại?
- [ ] Q430. **[P2]** Event contract nên độc lập đến đâu với database row hoặc payload riêng của một provider?

## 8.8 Lag, retry, poison message & Phục hồi

**Chuỗi hỏi:** Q431 → Q432 → Q433 → Q434 → Q435 → Q436 → Q437.

- [ ] Q431. **[P0]** Consumer lag là gì; bạn phân biệt traffic spike tạm thời với consumer không đủ năng lực xử lý như thế nào?
- [ ] Q432. **[P1]** Consumer offline lâu hơn retention thì bạn còn những hướng khôi phục nào?
- [ ] Q433. **[P1]** Lag vẫn tăng sau khi thêm consumers; bạn tách partition skew, slow downstream, rebalance và broker bottleneck ra sao?
- [ ] Q434. **[P1]** Poison message khác transient failure thế nào, và có nên dùng cùng retry policy không?
- [ ] Q435. **[P1]** Đưa event lỗi sang retry topic trong khi cho events sau tiếp tục sẽ ảnh hưởng business ordering thế nào?
- [ ] Q436. **[P2]** Dead-letter event cần giữ thông tin gì để điều tra và replay mà không tạo side effect sai?
- [ ] Q437. **[P2]** Bạn chọn bỏ qua, tạm dừng partition hay cô lập event lỗi dựa trên yêu cầu correctness và availability nào?

---

# 9. WebSocket & Hệ thống thời gian thực

Nguồn: [websocket/tutor.md](Computer%20Science/websocket/tutor.md).

## 9.1 Protocol & So sánh cách truyền cập nhật

**Chuỗi hỏi:** Q438 → Q439 → Q440 → Q441 → Q442.

- [ ] Q438. **[P0]** Persistent full-duplex WebSocket cho phép làm gì mà một HTTP request-response thông thường không đáp ứng trực tiếp?
- [ ] Q439. **[P0]** Bạn mô tả WebSocket handshake qua HTTP/1.1 như thế nào, và điều gì thay đổi sau khi server chấp nhận upgrade?
- [ ] Q440. **[P1]** Sec-WebSocket-Key kiểm tra điều gì trong handshake; nó có xác thực người dùng không?
- [ ] Q441. **[P1]** Polling, long polling, SSE và WebSocket khác nhau thế nào về chiều truyền, độ trễ và tài nguyên kết nối?
- [ ] Q442. **[P1]** Trong một ứng dụng real-time, bạn phân chia thao tác giữa REST API, WebSocket và background job theo tiêu chí nào?

## 9.2 Frame, control message & Application protocol

**Chuỗi hỏi:** Q443 → Q444 → Q445 → Q446 → Q447 → Q448 → Q449.

- [ ] Q443. **[P1]** WebSocket message khác frame thế nào, và vì sao một message có thể trải qua nhiều frames?
- [ ] Q444. **[P2]** JSON text và binary serialization đánh đổi gì về kích thước, chi phí xử lý và khả năng debug?
- [ ] Q445. **[P3]** Vì sao client-to-server frames được mask, và masking khác encryption ở đâu?
- [ ] Q446. **[P1]** Ping/pong, application heartbeat và close handshake giải quyết các vấn đề khác nhau nào của long-lived connection?
- [ ] Q447. **[P2]** Nếu xây trên raw WebSocket, bạn định nghĩa message type, correlation, error và versioning như thế nào?
- [ ] Q448. **[P3]** Subprotocol negotiation giải quyết nhu cầu gì, và khi nào nên dùng một protocol như STOMP?
- [ ] Q449. **[P2]** STOMP bổ sung messaging semantics nào, và điều gì thay đổi khi Spring simple broker trong RAM chạy trên nhiều instances?

## 9.3 Delivery, acknowledgment & Ordering

**Chuỗi hỏi:** Q450 → Q451 → Q452 → Q453 → Q454 → Q455.

- [ ] Q450. **[P0]** WebSocket send thành công cho biết gì về việc bên nhận đã nhận, lưu hoặc xử lý message?
- [ ] Q451. **[P0]** Sent, delivered và read cần được xác định bởi những sự kiện hoặc thành phần nào trong chat system?
- [ ] Q452. **[P1]** Server lưu message rồi socket đóng trước khi application ACK về sender; retry nên có hành vi gì?
- [ ] Q453. **[P1]** Recipient offline thì authoritative message history nên được quản lý thế nào để tiếp tục delivery khi họ quay lại?
- [ ] Q454. **[P0]** Ordering trên một WebSocket connection khác ordering qua nhiều connections hoặc asynchronous workers thế nào?
- [ ] Q455. **[P2]** Hai clients gửi updates cạnh tranh qua hai sockets; backend xác định thứ tự hợp lệ ra sao, và vì sao client timestamps có thể không đủ?

## 9.4 Reconnect, catch-up & Presence

**Chuỗi hỏi:** Q456 → Q457 → Q458 → Q459 → Q460 → Q461.

- [ ] Q456. **[P1]** Client đổi mạng mà không đóng socket cũ; server phát hiện connection chết và dọn state bằng cách nào?
- [ ] Q457. **[P1]** Sau reconnect, ngoài mở socket mới thì client cần khôi phục những trạng thái nào?
- [ ] Q458. **[P1]** Client tìm và nhận lại updates bị bỏ lỡ khi offline thế nào mà không hiển thị duplicate?
- [ ] Q459. **[P2]** Trong lúc catch-up lịch sử, live events mới tiếp tục đến; bạn xử lý ranh giới giữa hai luồng dữ liệu ra sao?
- [ ] Q460. **[P1]** Một user mở ba tabs và một điện thoại; bạn xác định online status thế nào khi từng connection đóng?
- [ ] Q461. **[P2]** Node chết trước khi xóa shared connection registry entries thì routing và presence phục hồi stale state bằng cách nào?

## 9.5 Xác thực, phân quyền & Abuse control

**Chuỗi hỏi:** Q462 → Q463 → Q464 → Q465 → Q466.

- [ ] Q462. **[P0]** Bạn xác thực browser WebSocket connection và gắn incoming messages với trusted user identity như thế nào?
- [ ] Q463. **[P1]** Kiểm tra quyền lúc handshake đã đủ cho mọi subscription và message về sau chưa?
- [ ] Q464. **[P1]** Token hết hạn hoặc quyền người dùng bị thu hồi trong khi socket vẫn mở thì hệ thống nên xử lý thế nào?
- [ ] Q465. **[P2]** Cookie-authenticated WebSocket có thể bị lạm dụng từ website khác ra sao, và Origin validation có vai trò gì?
- [ ] Q466. **[P1]** Bạn giới hạn hoặc kiểm tra gì để oversized messages và message floods không làm server cạn tài nguyên?

## 9.6 Horizontal scaling & Cross-node delivery

**Chuỗi hỏi:** Q467 → Q468 → Q469 → Q470 → Q471 → Q472.

- [ ] Q467. **[P0]** Sender và recipient nối vào hai WebSocket instances khác nhau; event đi đến đúng socket bằng cách nào?
- [ ] Q468. **[P1]** Sticky session hỗ trợ việc gì và không giải quyết được những bài toán cross-server messaging nào?
- [ ] Q469. **[P1]** Redis Pub/Sub và durable event log khác nhau ra sao khi làm backbone phân phối updates giữa WebSocket nodes?
- [ ] Q470. **[P2]** Mọi gateways cùng thuộc một Kafka group thì chuyện gì xảy ra nếu event được giao cho node không giữ socket của recipient?
- [ ] Q471. **[P1]** WebSocket gateway nên giữ những trách nhiệm gì và tách phần business processing nào ra ngoài?
- [ ] Q472. **[P1]** Handshake thành công nhưng socket thường bị ngắt lúc idle; bạn kiểm tra reverse proxy và load balancer ở đâu?

## 9.7 Connection capacity, backpressure & Fan-out

**Chuỗi hỏi:** Q473 → Q474 → Q475 → Q476 → Q477 → Q478 → Q479.

- [ ] Q473. **[P1]** Vì sao capacity planning cho WebSocket không thể chỉ dùng requests per second; mỗi idle connection tiêu thụ gì?
- [ ] Q474. **[P1]** Event loop quản lý nhiều idle sockets thế nào, và điều gì xảy ra nếu handler chạy blocking hoặc CPU-intensive quá lâu?
- [ ] Q475. **[P1]** Client đọc chậm hơn server gửi có thể làm server RAM tăng như thế nào, và bạn phát hiện bằng metric nào?
- [ ] Q476. **[P1]** Backpressure policy nên khác nhau thế nào giữa chat message cần lưu, typing indicator và dashboard chỉ cần latest value?
- [ ] Q477. **[P2]** Một event phải đến 100.000 subscribers; bạn ước lượng tổng công việc và xác định bottleneck fan-out như thế nào?
- [ ] Q478. **[P2]** Một WebSocket server nhiều clients bị crash có thể gây reconnect storm lên các servers còn lại ra sao?
- [ ] Q479. **[P2]** Bạn triển khai phiên bản mới thế nào để drain connections và tránh clients reconnect đồng loạt?

---

# 10. Docker, Containers & Vận hành

Nguồn: [docker/tutor.md](Computer%20Science/docker/tutor.md).

## 10.1 Image, container & Kernel isolation

**Chuỗi hỏi:** Q480 → Q481 → Q482 → Q483 → Q484 → Q485 → Q486 → Q487 → Q488.

- [ ] Q480. **[P0]** Docker giải quyết những vấn đề môi trường nào ngoài việc đóng gói JAR hoặc source code?
- [ ] Q481. **[P0]** Image khác container về tính bất biến, filesystem và runtime state như thế nào?
- [ ] Q482. **[P0]** Linux container khác virtual machine về kernel sharing, startup, tài nguyên và khả năng cô lập ra sao?
- [ ] Q483. **[P1]** Một Ubuntu container có userspace của hệ điều hành thì có nghĩa nó boot kernel riêng không?
- [ ] Q484. **[P0]** Namespaces khác cgroups về vai trò như thế nào?
- [ ] Q485. **[P1]** PID, network và mount namespaces thay đổi cách process nhìn các tài nguyên host ra sao?
- [ ] Q486. **[P2]** User namespace và rootless Docker thay đổi quyền của root trong container như thế nào?
- [ ] Q487. **[P2]** Linux container chạy trên macOS hoặc Windows cần một môi trường kernel như thế nào?
- [ ] Q488. **[P1]** Bạn mô tả từ docker run qua CLI, daemon, runtime đến lúc application process chạy như thế nào?

## 10.2 Build, layers & Reproducibility

**Chuỗi hỏi:** Q489 → Q490 → Q491 → Q492 → Q493 → Q494 → Q495 → Q496.

- [ ] Q489. **[P1]** Image layers giúp chia sẻ storage và tái sử dụng build cache bằng cách nào?
- [ ] Q490. **[P1]** Vì sao thường copy dependency manifests trước source code trong Dockerfile?
- [ ] Q491. **[P1]** Multistage build tách build environment và runtime image như thế nào; lợi ích và chi phí là gì?
- [ ] Q492. **[P1]** RUN khác CMD và ENTRYPOINT về thời điểm và mục đích thực thi như thế nào?
- [ ] Q493. **[P1]** Bạn phân biệt cấu hình build-time với runtime thế nào để dùng cùng image ở nhiều môi trường?
- [ ] Q494. **[P1]** Build context và .dockerignore ảnh hưởng build speed, cache invalidation và nguy cơ lộ file như thế nào?
- [ ] Q495. **[P1]** Nếu secret được thêm ở một layer rồi xóa ở layer sau, image được chia sẻ còn có thể lộ secret không?
- [ ] Q496. **[P1]** Image tag khác digest về khả năng tái tạo và rollback deployment như thế nào?

## 10.3 Writable layer, volumes & Overlay filesystem

**Chuỗi hỏi:** Q497 → Q498 → Q499 → Q500 → Q501 → Q502.

- [ ] Q497. **[P0]** Dữ liệu trong writable layer thay đổi thế nào khi stop, restart hoặc remove container?
- [ ] Q498. **[P0]** Bạn chọn volume, bind mount hay writable layer dựa trên vòng đời và cách truy cập dữ liệu như thế nào?
- [ ] Q499. **[P2]** Overlay filesystem tạo merged view ra sao, và lần ghi đầu vào file từ lower layer có thể tốn chi phí gì?
- [ ] Q500. **[P1]** Volume bảo vệ dữ liệu khỏi xóa container nhưng có bảo vệ khỏi mất host không; còn cần những cơ chế nào?
- [ ] Q501. **[P2]** Mount một host directory vào container có thể thay đổi mức cô lập và gây vấn đề permissions như thế nào?
- [ ] Q502. **[P3]** Classic storage driver và containerd snapshotter khác nhau về trách nhiệm quản lý image filesystem ở đâu; bạn xác minh backend đang dùng thế nào?

## 10.4 Container networking & Compose

**Chuỗi hỏi:** Q503 → Q504 → Q505 → Q506 → Q507 → Q508 → Q509 → Q510 → Q511 → Q512.

- [ ] Q503. **[P0]** localhost bên trong backend container chỉ tới đâu nếu PostgreSQL ở container khác?
- [ ] Q504. **[P1]** Containers trong user-defined bridge tìm nhau và giao tiếp bằng service name như thế nào?
- [ ] Q505. **[P1]** Bind address của ứng dụng, EXPOSE và published host port ảnh hưởng khả năng truy cập khác nhau ra sao?
- [ ] Q506. **[P1]** Backend và database cùng network có cần publish database port ra host để kết nối không?
- [ ] Q507. **[P2]** Host networking thay đổi isolation, port conflicts và network translation so với bridge như thế nào?
- [ ] Q508. **[P2]** Khi nào chọn network none, và container khi đó còn khả năng giao tiếp nào?
- [ ] Q509. **[P1]** Vì sao hard-code container IP có thể hỏng sau khi recreate, và service discovery giúp gì?
- [ ] Q510. **[P1]** Docker Compose quản lý services, networks và volumes khác việc chạy từng container thủ công như thế nào?
- [ ] Q511. **[P1]** depends_on theo thứ tự khởi động có đủ bảo đảm database đã sẵn sàng nhận queries không?
- [ ] Q512. **[P1]** Container running nhưng API không truy cập được; bạn tách lỗi binding, port mapping, DNS và network như thế nào?

## 10.5 Lifecycle, resource limits & Orchestration

**Chuỗi hỏi:** Q513 → Q514 → Q515 → Q516 → Q517 → Q518 → Q519 → Q520 → Q521 → Q522 → Q523.

- [ ] Q513. **[P1]** Vì sao container có thể dừng ngay sau khi khởi động dù image build thành công?
- [ ] Q514. **[P1]** PID 1 có những trách nhiệm đặc biệt nào với signal handling và child-process reaping?
- [ ] Q515. **[P1]** Shell-form entrypoint có thể ảnh hưởng graceful shutdown khác exec-form như thế nào?
- [ ] Q516. **[P1]** docker stop cho process cơ hội kết thúc thế nào trước khi bị force kill?
- [ ] Q517. **[P1]** Health check khác việc thấy process còn sống như thế nào; trạng thái unhealthy tự động dẫn đến những hành động nào còn tùy runtime hoặc orchestrator?
- [ ] Q518. **[P1]** CPU throttling và memory limit có thể làm ứng dụng chậm hoặc bị kill dù host còn tài nguyên như thế nào?
- [ ] Q519. **[P1]** Java container bị OOM-kill khi heap chưa đầy; bạn kiểm tra những phần bộ nhớ nào và phân biệt với Java heap OOM ra sao?
- [ ] Q520. **[P2]** Bạn dùng exit state, logs, runtime configuration và resource metrics để điều tra container restart như thế nào?
- [ ] Q521. **[P1]** Giữ local session hoặc job state trong container tạo khó khăn gì khi scale và recreate instances?
- [ ] Q522. **[P2]** Những yêu cầu scheduling, self-healing và rolling deployment nào khiến cần thêm orchestrator?
- [ ] Q523. **[P2]** Compose và Kubernetes khác nhau thế nào cho ứng dụng nhỏ trên một host và workload nhiều máy?

## 10.6 Security & Ranh giới vận hành

**Chuỗi hỏi:** Q524 → Q525 → Q526 → Q527 → Q528 → Q529.

- [ ] Q524. **[P1]** Container chia sẻ host kernel thì những rủi ro nào vẫn còn dù đã dùng namespaces?
- [ ] Q525. **[P1]** Non-root user, reduced capabilities và read-only filesystem giới hạn ứng dụng theo những hướng khác nhau nào?
- [ ] Q526. **[P2]** Seccomp giới hạn bề mặt nào khác permissions hoặc Linux capabilities?
- [ ] Q527. **[P1]** Vì sao quyền truy cập Docker daemon hoặc docker.sock có thể tạo quyền rất mạnh trên host?
- [ ] Q528. **[P2]** Dùng privileged mode để sửa permission error có thể gây hậu quả gì, và bạn sẽ xác định quyền tối thiểu như thế nào?
- [ ] Q529. **[P1]** Bạn đưa runtime secrets vào container thế nào mà không đóng gói chúng vào image hoặc source được chia sẻ?

---

# 11. Bổ sung từ CV — Các hướng có thể bị hỏi thêm

Nguồn: [CV — nguyenvanquan_om.pdf, trang 1–2](/home/quan3054/cv/nguyenvanquan_om.pdf).

## 11.1 Payment integration, idempotency & Reconciliation

**Chuỗi hỏi:** Q530 → Q531 → Q532 → Q533 → Q534 → Q535 → Q536 → Q537 → Q538 → Q539 → Q540 → Q541 → Q542 → Q543 → Q544 → Q545.

- [ ] Q530. **[P0]** [CV] Trong NAPAS và SePay integration, bạn trực tiếp phụ trách những phần nào, và có thể mô tả một giao dịch từ lúc tạo yêu cầu đến trạng thái cuối không?
- [ ] Q531. **[P0]** [CV] Payment request timeout nhưng provider có thể đã xử lý; trong hệ thống bạn làm, bạn quyết định retry, inquiry hay chờ webhook như thế nào?
- [ ] Q532. **[P0]** [CV] Idempotency bảo vệ business effect nào trong payment workflow của bạn, và khóa định danh được giới hạn theo phạm vi gì?
- [ ] Q533. **[P1]** [CV] Nếu cùng idempotency key được gửi lại với amount hoặc beneficiary khác, hệ thống của bạn xử lý ra sao?
- [ ] Q534. **[P1]** [CV] Provider xử lý thành công nhưng service crash trước khi lưu kết quả; thiết kế của bạn phục hồi khoảng trống đó như thế nào?
- [ ] Q535. **[P1]** [CV] Bạn chọn transaction boundary, uniqueness constraints và các trạng thái trung gian cụ thể nào trong payment workflow?
- [ ] Q536. **[P1]** [CV] Bạn xử lý callbacks đến trễ hoặc đảo thứ tự thế nào để trạng thái cũ không ghi đè trạng thái mới?
- [ ] Q537. **[P1]** [CV] Vì sao hệ thống vẫn cần D-1 hoặc end-of-day reconciliation bên cạnh real-time synchronization?
- [ ] Q538. **[P1]** [CV] Bạn so khớp records bằng identifiers và quy tắc nào để phân biệt missing, duplicate, status mismatch và amount mismatch?
- [ ] Q539. **[P1]** [CV] Bạn định nghĩa ranh giới ngày và xử lý timezone của provider thế nào trong reconciliation?
- [ ] Q540. **[P1]** [CV] Paginated provider data thay đổi trong lúc tải thì batch của bạn tránh bỏ sót hoặc xử lý lặp records ra sao?
- [ ] Q541. **[P1]** [CV] Per-record failure isolation, resume và audit logs trong reconciliation hoặc export jobs của bạn được thiết kế thế nào?
- [ ] Q542. **[P2]** [CV] Bạn biểu diễn amount, currency và provider transaction ID thế nào để tránh lỗi precision hoặc serialization giữa Java, TypeScript và JSON?
- [ ] Q543. **[P1]** [CV] Strategy và Factory giải quyết những coupling nào trong connector của bạn, và mỗi abstraction chịu trách nhiệm gì?
- [ ] Q544. **[P2]** [CV] Một provider có capability hoặc status không khớp interface chung thì bạn mở rộng mô hình như thế nào?
- [ ] Q545. **[P2]** [CV] Linked-bank connection lifecycle và payment authorization được kiểm tra ra sao trước khi khởi tạo giao dịch mới?

## 11.2 Omnichannel chat, Bull/BullMQ & Tenant isolation

**Chuỗi hỏi:** Q546 → Q547 → Q548 → Q549 → Q550 → Q551 → Q552 → Q553 → Q554 → Q555 → Q556 → Q557 → Q558 → Q559.

- [ ] Q546. **[P0]** [CV] Trong hệ thống UpBase, Kafka và Bull/BullMQ xử lý những workloads nào, và vì sao không dùng cùng một cơ chế cho tất cả?
- [ ] Q547. **[P0]** [CV] Bạn mô tả notification pipeline thực tế từ webhook, database, enqueue đến worker và WebSocket như thế nào?
- [ ] Q548. **[P0]** [CV] Deterministic job IDs của bạn được tạo từ những trường nào, và hai jobs thế nào được coi là cùng một notification?
- [ ] Q549. **[P1]** [CV] Completed job đã bị xóa rồi notification tương tự được enqueue lại thì cơ chế nào còn ngăn duplicate delivery?
- [ ] Q550. **[P1]** [CV] Bull worker gửi notification thành công nhưng crash trước khi ghi completion; hệ thống bạn làm xử lý lần chạy lại ra sao?
- [ ] Q551. **[P1]** [CV] Worker bị xem là stalled nhưng sau đó vẫn tiếp tục chạy thì workflow của bạn tránh hai workers gây side effect đồng thời thế nào?
- [ ] Q552. **[P1]** [CV] Bạn chọn retry limits, backoff và thời điểm ngừng retry dựa trên những loại provider failures nào?
- [ ] Q553. **[P1]** [CV] Provider rate limit được chia sẻ giữa nhiều workers và stores thì bạn điều phối quota và cô lập store lỗi thế nào?
- [ ] Q554. **[P0]** [CV] Trong chat webhook processing, vì sao bạn dùng cả PostgreSQL uniqueness constraints và Redis locks; mỗi cơ chế bảo vệ invariant nào?
- [ ] Q555. **[P1]** [CV] Bạn chọn phạm vi lock key và unique constraint thế nào để tránh collisions giữa tenant, store và provider?
- [ ] Q556. **[P0]** [CV] Bạn ngăn người dùng đổi conversation ID để truy cập dữ liệu store khác ở những tầng nào?
- [ ] Q557. **[P1]** [CV] Quyền truy cập store bị thu hồi sau lúc enqueue job hoặc mở WebSocket thì công việc đang chờ và subscriptions xử lý ra sao?
- [ ] Q558. **[P1]** [CV] Bạn chuẩn hóa message và delivery states của Facebook, Shopee, TikTok, Lazada thế nào mà vẫn giữ khác biệt quan trọng của từng provider?
- [ ] Q559. **[P1]** [CV] Một người dùng báo thiếu update; bạn truy vết qua provider webhook, queued job, WebSocket gateway và client bằng những identifiers nào?

## 11.3 Spring, NestJS, GraphQL & Distributed patterns

**Chuỗi hỏi:** Q560 → Q561 → Q562 → Q563 → Q564 → Q565 → Q566 → Q567 → Q568 → Q569 → Q570 → Q571.

- [ ] Q560. **[P0]** [CV] Microservices trong CV tương ứng những service boundaries thực tế nào, và bạn có thể nêu trade-off của một boundary đã tham gia thiết kế không?
- [ ] Q561. **[P0]** [CV] Bạn đã áp dụng Transactional Outbox và Saga trực tiếp, trong thử nghiệm hay mới học; có thể dùng một tình huống cụ thể để chứng minh phạm vi hiểu biết không?
- [ ] Q562. **[P1]** [CV] Với outbox, bạn ghi những dữ liệu nào trong cùng local transaction và xử lý relay crash sau publish nhưng trước ghi nhận completion thế nào?
- [ ] Q563. **[P1]** [CV] Với saga, compensation khác rollback ra sao, và thiết kế của bạn xử lý compensation thất bại hoặc bước không thể đảo ngược như thế nào?
- [ ] Q564. **[P1]** [CV] Dependency injection và scope trong Spring hoặc NestJS tác động thế nào tới shared state và khả năng kiểm thử các components bạn viết?
- [ ] Q565. **[P0]** [CV] @Transactional trong Spring bao bọc lời gọi nào, và bạn xác minh transaction thực sự có hiệu lực bằng cách nào?
- [ ] Q566. **[P1]** [CV] Self-invocation, exception handling hoặc chuyển sang asynchronous task có thể làm transaction boundary khác dự kiến thế nào?
- [ ] Q567. **[P1]** [CV] Khi dùng Spring Data JPA, bạn phân biệt persistence context, flush và commit ra sao, và đã gặp N+1 hoặc lazy-loading issue nào chưa?
- [ ] Q568. **[P1]** [CV] Vì sao dùng GraphQL trong hệ thống của bạn, và bạn kiểm soát query cost cùng tenant authorization ở resolver như thế nào?
- [ ] Q569. **[P1]** [CV] Bạn xác minh webhook authenticity, xử lý replay và bảo vệ provider credentials trong integrations bằng cách nào?
- [ ] Q570. **[P2]** [CV] Hai workers cùng refresh provider token hết hạn thì bạn ngăn chúng ghi đè credentials mới như thế nào?
- [ ] Q571. **[P2]** [CV] Khi triển khai logic tương tự bằng Spring, NestJS hoặc Laravel, bạn phải kiểm tra lại những giả định về validation, concurrency và transaction nào?

## 11.4 Java 8 toolkit, JavaFX & Bằng chứng kỹ thuật

**Chuỗi hỏi:** Q572 → Q573 → Q574 → Q575 → Q576 → Q577 → Q578 → Q579 → Q580 → Q581 → Q582 → Q583.

- [ ] Q572. **[P0]** [CV] Trong toolkit Java Core 8 tại FPT, processing engine, rule evaluation và reporting được chia trách nhiệm như thế nào?
- [ ] Q573. **[P1]** [CV] Với N source files và R rules, bạn ước lượng time/space complexity và tìm phần đáng tối ưu đầu tiên ra sao?
- [ ] Q574. **[P1]** [CV] Bạn chọn list, set hoặc map để gom findings và tránh trùng kết quả như thế nào; equals/hashCode ảnh hưởng correctness ra sao?
- [ ] Q575. **[P1]** [CV] Những phần nào trong toolkit có thể chạy song song, và shared state hoặc thứ tự output nào cần coordination?
- [ ] Q576. **[P1]** [CV] Bạn giữ JavaFX UI responsive trong khi chạy tests dài và chuyển kết quả worker về UI an toàn bằng cách nào?
- [ ] Q577. **[P2]** [CV] Bạn hỗ trợ cancel, shutdown và xử lý input hoặc report lớn hơn RAM như thế nào?
- [ ] Q578. **[P0]** [CV] CV mô tả high-traffic và high-volume workflows; bạn có thể nêu workload measurements thực tế và bottleneck đã quan sát được không?
- [ ] Q579. **[P1]** [CV] Bạn kể một production incident đã điều tra, bằng chứng xác định nguyên nhân và cách chứng minh bản sửa có hiệu quả như thế nào?
- [ ] Q580. **[P1]** [CV] Grafana và structured logs của bạn theo dõi gì để phân biệt provider delay, queue backlog và processing failure?
- [ ] Q581. **[P1]** [CV] Bạn kiểm thử duplicate callbacks, event reordering và crash tại các ranh giới side effect như thế nào?
- [ ] Q582. **[P2]** [CV] CI/CD của bạn bảo đảm artifact được kiểm thử là artifact được deploy và có thể rollback như thế nào?
- [ ] Q583. **[P2]** [CV] Trong AI-assisted development, bạn kiểm chứng độc lập những thay đổi ảnh hưởng concurrency hoặc transaction correctness bằng cách nào?

---
