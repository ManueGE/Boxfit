NOTES:

- When genrating code, disallow JsonSerializable in generic classes, but allow it in its concrete subclases.
- Check if expected to one relationship is a primitive, and build jsonobject to allow import. 
- Same with tomany
- Check with entities without id in the json
- Test jsons with null values
- Test jsons without primary key