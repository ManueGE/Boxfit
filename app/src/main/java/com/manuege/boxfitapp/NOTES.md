# NOTES:

## Known issues
- Lists must be initialized (ToMany already does it)
- JsonSerializable not allowed in generic classes, but allow it in its concrete subclasses.

## Pending tests
- Test objects without primary key (or not-assignable PK)


### To Json
- add test for `ToJsonIgnore`
- add tests for `ToJsonIgnoreNull` taking in account all type of fields kind (to many, normal...), primitives...
