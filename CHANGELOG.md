# Boxfit

### 0.0.5 (9 July 2018)
- Add `ToJsonAsId` annotation. This allows using ids instead of the full object when converting entities to JSON.
- Lists of primitive values (`string`, `int`, `double`, `bool`, `long`) are supported. (They must use the proper `ObjectBox` converter).

### 0.0.4 (28 June 2018)
- Add `BoxfitId` annotation. This allow using different types of Id instead of `long`. 

### 0.0.3 (22 May 2018)
- Add `FromJsonIgnoreNull` annotation to ignore null values during serialization. ([issue #3](https://github.com/ManueGE/Boxfit/issues/3))

### 0.0.2 (12 May 2018)
- Fix Kotlin compatibility ([issue #2](https://github.com/ManueGE/Boxfit/issues/2))
- Support **ObjectNox 1.5**.


### 0.0.1 (28 March 2018)
- Initial version
