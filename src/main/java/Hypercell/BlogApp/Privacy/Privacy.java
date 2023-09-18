package Hypercell.BlogApp.Privacy;

import lombok.Getter;

@Getter
public enum Privacy {
    PUBLIC("0"),
    FRIENDS("1"),
    ONLY_ME("2");

    private final String privacyVal;

    private Privacy(String privacyVal) {
        this.privacyVal = privacyVal;
    }
}
