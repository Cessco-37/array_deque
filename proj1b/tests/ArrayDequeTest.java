import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

    @Test
    @DisplayName("ArrayDeque has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    @DisplayName("Test get method with valid index")
    void testGetValidIndex() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.get(0)).isEqualTo(1);
        assertThat(deque.get(1)).isEqualTo(2);
        assertThat(deque.get(2)).isEqualTo(3);
    }

    @Test
    @DisplayName("Test get method with negative index")
    void testGetNegativeIndex() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);

        assertThat(deque.get(-1)).isNull();
    }

    @Test
    @DisplayName("Test get method with index exceeding size")
    void testGetIndexExceedingSize() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);

        assertThat(deque.get(2)).isNull();
    }

    @Test
    @DisplayName("Test get method after resizing")
    void testGetAfterResizing() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
        }

        assertThat(deque.get(1)).isEqualTo(1);
        assertThat(deque.get(9)).isEqualTo(9);
    }
    @Test
    @DisplayName("Test removeFirst")
    void testRemoveFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque.removeFirst()).isEqualTo(2);
        assertThat(deque.removeFirst()).isEqualTo(3);
        assertThat(deque.removeFirst()).isNull();
    }

    @Test
    @DisplayName("Test removeLast")
    void testRemoveLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.removeLast()).isEqualTo(3);
        assertThat(deque.removeLast()).isEqualTo(2);
        assertThat(deque.removeLast()).isEqualTo(1);
        assertThat(deque.removeLast()).isNull();
    }
    @Test
    @DisplayName("Test addFirst")
    void testAddFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.addFirst(2);

        assertThat(deque.removeFirst()).isEqualTo(2);
        assertThat(deque.removeFirst()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test addLast")
    void testAddLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);

        assertThat(deque.removeLast()).isEqualTo(2);
        assertThat(deque.removeLast()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test isEmpty")
    void testIsEmpty() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        assertThat(deque.isEmpty()).isTrue();

        deque.addFirst(1);
        assertThat(deque.isEmpty()).isFalse();

        deque.removeFirst();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Test size")
    void testSize() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        assertThat(deque.size()).isEqualTo(0);

        deque.addFirst(1);
        assertThat(deque.size()).isEqualTo(1);

        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(0);
    }
    @Test
    @DisplayName("Test add after remove")
    void testAddAfterRemove() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque.removeFirst()).isEqualTo(2);

        deque.addLast(4);
        deque.addLast(5);

        assertThat(deque.removeFirst()).isEqualTo(3);
        assertThat(deque.removeFirst()).isEqualTo(4);
        assertThat(deque.removeFirst()).isEqualTo(5);

        // Test adding after removing to empty
        deque.addFirst(6);
        assertThat(deque.removeFirst()).isEqualTo(6);
    }

    @Test
    @DisplayName("Test remove")
    void testRemove() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque.removeLast()).isEqualTo(3);

        assertThat(deque.removeFirst()).isEqualTo(2);
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.removeLast()).isNull();

        deque.addLast(4);
        assertThat(deque.removeFirst()).isEqualTo(4);
    }

    @Test
    @DisplayName("Test toList")
    void testToList() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        List<Integer> list = deque.toList();
        assertThat(list).containsExactly(1, 2, 3).inOrder();

        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        list = deque.toList();
        assertThat(list).isEmpty();
    }
}