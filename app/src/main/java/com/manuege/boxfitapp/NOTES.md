# NOTES:

## To do
- reuse transformers

## Known issues
- Lists must be initialized (ToMany already does it)

## Code generation checks
- disallow JsonSerializable in generic classes, but allow it in its concrete subclasses.
- Transformers must have empty initializer
- Transformer can be added just to properties, no relationships
- Make distinction between primary types (must have a default value) and classes (don't need it)
- Just strings and longs valid as PK

## Pending tests
- Maybe write a test to check all the types
- Test objects without primary key (or not-assignable PK)
- Test to many with List<> and ToMany<>
- Test paginated responses with List, ArrayList...
- Test a not entity with response with a single generic object:

```
class ObjectResponse<T extends Entity> {
    T result;
}
```

## To Json
- add test for `ToJsonIgnore`
- add tests for `ToJsonIgnoreNull` taking in account all type of fields kind (to many, normal...), primitives...
