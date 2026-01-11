package ru.shashy.enums;

import lombok.Getter;

@Getter
public enum ZapretEnum {

    ASSET_NAME("zapret_latest.zip"),
    ZAPRET_AUTO_NAME("ZapretAuto");

    private final String name;

    ZapretEnum(String name) {
        this.name = name;
    }
}