package com.laerciorodrsf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.laerciorodrsf.listeners.InstagramListener;

public class InstagramListenerTest {

    private final InstagramListener listener = new InstagramListener();

    @Test
    void shouldConvertInstagramLink() {
        String message = "https://instagram.com/p/abc123";
        List<String> result = listener.convertInstagramUrl(message);

        assertEquals("https://kkinstagram.com/p/abc123", result.get(0));
    }

    @Test
    void shouldConvertInstagramLinkWithAditionalText() {
        String message = "Take a look at this post: https://instagram.com/p/abc123";
        List<String> result = listener.convertInstagramUrl(message);

        assertEquals("https://kkinstagram.com/p/abc123", result.get(0));
    }

    @Test
    void shouldConvertWithWww() {
        String message = "https://www.instagram.com/reel/abc123";
        List<String> result = listener.convertInstagramUrl(message);

        assertEquals("https://www.kkinstagram.com/reel/abc123", result.get(0));
    }

    @Test
    void shouldNotConvertWhenMessageDoesNotMatches() {
        String message = "Hello, how are you?";
        List<String> result = listener.convertInstagramUrl(message);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldConvertMultipleLinks() {
        String message = """
                Link 1: https://instagram.com/p/abc
                Link 2: https://instagram.com/reel/xyz
                """;
        List<String> result = listener.convertInstagramUrl(message);

        assertEquals(2, result.size());
        assertEquals(
                "https://kkinstagram.com/p/abc",
                result.get(0));
        assertEquals(
                "https://kkinstagram.com/reel/xyz",
                result.get(1));
    }
}
