package cachemachine.helpers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ValidAmountsArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(1000, 500, 5),
                Arguments.of(1000, 320, 4),
                Arguments.of(500, 300, 3),
                Arguments.of(500, 270, 4),
                Arguments.of(1000, 995, 13)
        );
    }
}
