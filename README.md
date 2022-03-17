# Freepot Minting Tool

Tool for batch NFT minting.
Takes CSV file with NFT metadata, minter private key, assets and previews and runs minting and DDC upload in parallel.

## Usage

- Download the latest release for your platform
- Run binary file
- Navigate in browser to [`http://localhost:8080`](http://localhost:8080)
- Follow the instructions

## Features

- mint NFTs
- upload assets and previews to DDC
- attach metadata to NFT
- configure royalties
- set price

## Hardcoded configuration

|Environment|RPC|Chain ID|NFT Contract|Attachment Contract|DDC Proxy|Gas Price|Gas Limit|
|---|---|---|---|---|---|---|---|
|`DEV`|`https://rpc-mumbai.matic.today`|`80001`|`0x702BA959B5542B2Bf88a1C5924F73Ed97482c64B`|`0x39B27a0bc81C1366E2b05E02642Ef343a4f9223a`|`https://ddc.freeport.dev.cere.network`|`25 Gwei`|`9x10^6`|
|`STG`|`https://rpc-mumbai.matic.today`|`80001`|`0xacd8105cBA046307d2228794ba2F81aA15e82E0D`|`0x84766787c6b9131927A76634F7DDCfcf3ff2e9d1`|`https://ddc.freeport.stg.cere.network`|`25 Gwei`|`9x10^6`|
|`PROD`|`https://polygon-rpc.com`|`137`|`0xf9AC6022F786f6f64Fd8abf661190b8517D92396`|`0x651f2C6942F1c290632Ad5bB61D9ece789f82f35`|`https://ddc.freeport.cere.network`|`50 Gwei`|`9x10^6`|

## Requirements for development

- [JDK 11](https://adoptopenjdk.net) or higher
- (Optional) [GraalVM 21](https://quarkus.io/guides/building-native-image#configuring-graalvm) or higher
- (Optional) [UPX](https://upx.github.io)

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell
./gradlew quarkusDev
```

## Build application (JAR)

```shell
./gradlew quarkusBuild
```

## Build native executable (requires GraalVM)

```shell
./gradlew build -Dquarkus.package.type=native
```

## Build compressed native executable for release (requires UPX)

```shell
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.compression.level=10 -Dquarkus.native.compression.additional-args=--ultra-brute
```
