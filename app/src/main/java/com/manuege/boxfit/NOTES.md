# NOTES:

## Code generation checks
- disallow JsonSerializable in generic classes, but allow it in its concrete subclases.
- Transformers must have empty initializer
- Transformer can be added just to properties, no relationships

## Pending tests
- Test jsons with null values
- Test objects without primary key (or not-assignable PK)
- Test arrays of "wrappers"
- Test to many with List<> and ToMany<>