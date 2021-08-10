### build.gradle(:app)

```
android
|__defaultConfigs
|__signingConfigs
|__buildTypes
|__productFlavors
```

#### signingConfigs
- signingConfigs에서 빌드시 필요한 Sigend Key를 설정
- *jks*란: *J*ava *K*ey *S*tore의 약자로, SSL 암호화를 위해 쓰이는 key들을 모아놓은 파일.
- jre/lib/security/cacerts 디렉토리에 제공되는 기본 CA keystore를 사용하거나 별도의 keystore를 사용할 수 있다

#### buildTypes
- release
- debug

#### productFlavors
[Configure build variants](https://developer.android.com/studio/build/build-variants)


lambda expressions are not supported in language level 7
app/build 안에 BuildConfig 파일이 자동생성됨

