# NOTES:

## Code generation checks
- disallow JsonSerializable in generic classes, but allow it in its concrete subclasses.
- Transformers must have empty initializer
- Transformer can be added just to properties, no relationships
- Make distinction between primary types (must have a default value) and classes (don't need it)

## Pending tests
- Test objects without primary key (or not-assignable PK)
- Test arrays of "wrappers"
- Test to many with List<> and ToMany<>