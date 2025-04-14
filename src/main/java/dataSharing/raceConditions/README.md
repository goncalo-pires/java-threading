## Heap vs Stack in Java

In Java, memory is managed in two main areas: the **Heap** and the **Stack**. These two memory regions serve different purposes and handle variables and objects in different ways.

---

## **Stack Memory**:

### What is it?
- The stack is a region of memory where **local variables** and **method calls** are stored.
- It follows a **Last In, First Out (LIFO)** structure. Each time a method is called, a new **stack frame** is created and pushed onto the stack. Once the method completes, its stack frame is popped off.

### Stored Variables:
- **Primitive data types** (e.g., `int`, `char`, `boolean`).
- **References** to objects (not the objects themselves).

### Characteristics:
- Memory allocation and deallocation are fast (automatic).
- Memory is **limited** and typically smaller compared to the heap.
- The size of the stack is defined by the system or JVM.
- Variables in the stack are automatically cleared when a method exits or an object goes out of scope.

### Example:
- When a method is called, primitive values like `int`, `char` are stored in the stack. References to objects (such as arrays or custom objects) are also stored in the stack.

  ```java
  public class StackExample {
      public static void main(String[] args) {
          int a = 5;  // Primitive variable, stored in the stack.
          String str = "Hello";  // Reference to a String object, reference is stored in the stack, object is in the heap.
      }
  }

---

## Heap Memory

### What is it?

- The heap is a larger memory area used for storing objects created using the `new` keyword.
- It is more flexible in terms of memory allocation and size, but memory allocation and deallocation are slower compared to the stack.

### Stored Variables:
- **Objects** (including arrays, instances of classes).
- **Instance variables** and **fields** of objects.

### Characteristics:
- Memory is dynamically allocated at runtime.
- Memory deallocation happens via garbage collection, not automatic.
- The heap is larger than the stack.
- Objects in the heap are more persistent and can be accessed from different parts of the program (since references to the heap objects can be passed around).

### Example:
When you create a new object (like a `String` or a `CustomObject`), the actual object is created in the **heap**, while the reference to the object is stored in the **stack**.

```java
public class HeapExample {
    public static void main(String[] args) {
        String str = new String("Hello");  // Object created in heap.
    }
}
```

### 🔑 Key Differences Between Heap and Stack:

| Feature              | **Stack**                               | **Heap**                                    |
|----------------------|-----------------------------------------|---------------------------------------------|
| **Storage**          | Local variables, method calls, references to objects | Objects created with `new` keyword, instance variables |
| **Size**             | Smaller, system-defined size            | Larger, more flexible size                  |
| **Memory Allocation**| Fast, automatic (on method entry)       | Slower, handled via garbage collection      |
| **Scope**            | Limited to method or thread scope       | Accessible from anywhere in the program (via references) |
| **Lifespan**         | Short-lived (method exits)              | Long-lived (until garbage collection)       |
| **Deallocation**     | Automatic, when method exits            | Manual (via garbage collection)             |

---

### 📝 Takeaways:
- **Stack:** Primarily used for storing primitive types and method calls, and its memory management is automatic and fast.
- **Heap:** Used for storing objects, provides flexible memory but requires garbage collection to manage memory cleanup.
