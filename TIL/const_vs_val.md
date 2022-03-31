### `const` vs `val`
- `const val` : assigned when source code is compiled
- `val` : assigned when the application runs
- const는 문자열이나 기본 자료형으로 할당되어야 한다

### handling constants in kotlin
- not recommended : `companion object` block inside a class
	- (in kotlin) getter and setter instance methods are created for the fields to be accessible. Calling instance methods is technically more expensive than calling static methods.
- recommended : `const val` field in object class
- [reference](https://stackoverflow.com/questions/44038721/constants-in-kotlin-whats-a-recommended-way-to-create-them)