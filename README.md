# Boxfit

Puts together [**Retrofit**](http://square.github.io/retrofit/) and [**ObjectBox**](http://ObjectBox.io).


Convert a JSON response received through **Retrofit** to **ObjectBox** entities and save them into your `BoxStore` automatically.


## Install

```
dependencies {
    annotationProcessor 'com.manuege.boxfit:boxfit-processor:0.0.4'
    implementation 'com.manuege.boxfit:boxfit:0.0.4'
}
```

If you are using Kotlin, replace annotationProcessor with kapt.

## Usage

### Retrofit converter

To plug **Boxfit** into **Retrofit** you need to provide a `Converter.Factory`. To do so, you can write:

```java
Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(BoxfitSerializer.getConverterFactory(boxStore))
                .baseUrl(API_BASE_URL)
                .build();
```

Where `boxStore` is the `BoxStore` where all the objects will be saved.

When you write this code, you may have an error because `BoxfitSerializer` is not defined. No problem at all, just build your project and that class will be generated. 

 
### Json Serialization

**Boxfit** doesn't use **Gson**, **Jackson** or others to convert the HTTP responses into your objects and to insert them into your **BoxStore**. Instead, it generates a set of serializer classes that take care of this work. 

In order to let Boxfit know how to generate these serializers, you need to annotate your classes and fields.

### Annotations

Let's suppose you have a `User` class:

````java
@Entity
class User {
    @Id(assignable = true)
    long id;
    String username;
    String avatar;
}
````

To make this class able to be serialized with Boxfit you must annotate it with `@BoxfitClass`. Also, the fields whose values can be taken from a JSON must be annotated with `@BoxfitField`, like this:

```java
@BoxfitClass
@Entity
class User {
    @Id(assignable = true)
    @BoxfitField
    long id;

    @BoxfitField
    String username;
    
    @BoxfitField
    String avatar;
}
```

> **Note**: For simplicity, in the sample, we just show simple properties annotated with `@BoxfitField`, but you can also use it with relations (`ToOne`, `ToMany`, `List`). 

`@BoxfitClass` can be added to any class, whether it is an ObjectBox entity or not. The only condition is that the class can't be generic, but it could be a concrete subclass of a generic class.

`@BoxfitField` can be added to any type of field. However, only native JSON fields (numbers, strings, booleans, lists, and objects) can be serialized directly. Other types (dates, enums...) must be transformed before being serialized. More about this in the `Transformers` section. 

### Field names and JSON keys

#### Custom field name
If you don't specify the name of the JSON key under one value comes, **Boxfit** will use the field name **exactly as it is declared** (that means that it won't automatically convert *camelCase* to *snake_case*). If the field name and the JSON key has different values you can indicate it by adding the key into the `BoxfitField` annotation. For instance:

```java
@BoxfitField("name")
String username;
```

#### Dot notation

Let's suppose you have a JSON with this format:

```json
{
   "id": 1,
   "name": "Manuel",
   "media": {
       "avatar": {
           "url": "http://avatars.com/image.jpg"
       }
   }
}
```

You can use dot notation to indicate the full path where the avatar is located, like this:

```java
@BoxfitField("media.avatar.url")
String avatar;
```


### Transformers

#### Fields

> **Note**: Transformer just can be applied to property fields, not to relationships. 

Sometimes your field type is not compatible with JSON. Let's continue with the example of the `User` class. Now we will add a new field, `registerDate`. The date cames into a string format: `yyyy-MM-dd`. To transform a `String` into a `Date` we need to create a class that implements `Transformer`:

```java
public class MyStringToDateTransformer implements Transformer<Date, String> {
    @Override
    public Date transform(String object) {
        try {
            return getFormat().parse(object);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String inverseTransform(Date object) {
        if (object == null) {
            return null;
        }
        return getFormat().format(object);
    }

    private DateFormat getFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }
}
```

And now, you can modify the user class by adding the `Date`:

```java
@BoxfitClass
@Entity
class User {
    @Id(assignable = true)
    @BoxfitField
    long id;
    
    @BoxfitField("name")
    String username;
    
    @BoxfitField("media.avatar.url")
    String avatar;
    
    @BoxfitField(transformer = MyStringToDateTransformer.class)
    Date registerDate;
}
```

> **Note**: This is just an example for reference. Actually, converting `String` into `Date` and back is such a common case that **Boxfit** provides a built-in abstract class `StringToDateTransformer` that you can subclass by only providing a `DateFormat`.


#### Classes

Not only fields can be transformed. If for some reason you don't like the JSON you get from your server and you want to make some transformations into it before being serialized, you can do it by creating a class that implements `JSONObjectTransformer` and setting the `transformer` value of the `@BoxfitClass` annotation.


### String Ids

At this moment, ObjectBox just allow `long` as the type of the id of the entities. If you need to use a `String` as id, you can do it using the `BoxfitId` annotation:

```java
@Entity
class MyClass {
	// This the id required by ObjectBox
	@Id
	long _id;
	
	// This is the id used by Boxfit
	@BoxfitId
	@BoxfitField
	String id;
}
```
##### Note:
Please, keep in mind that this id is just used by Boxfit to identify existing objects and update them. Nothing prevents you from adding two objects with the same value of this `id` in the database, so use this feature wisely. 

### Features
#### Updating objects
Let's suppose that at some point we get this JSON:

```json
{
   "id": 1,
   "name": "Manuel",
   "media": {
       "avatar": {
           "url": "http://avatars.com/image.jpg"
       }
   }
}
```

After serializing this JSON, we will have a `User` object with its `id`, `name` and `avatar`. 

Sometime later, we get this one:

```json
{
   "id": 1,
   "name": "Marco",
}
```

As you can see, the name for the user `1` has been updated and also this second JSON doesn't have the `avatar` key path so, what will happen with the `User` stored in the `BoxStore`?

The answer is that the name will be updated, but the `avatar` won't be modified. Any missing key in the JSON will be ignored and the field will keep its current value. 

However, if we have this JSON:

```json
{
   "id": 1,
   "name": "Manuel",
   "media": {
       "avatar": {
           "url": null
       }
   }
}
```

the `avatar` of the user will be updated and set to `null`.

#### Ignore null

The behaviour defined before can be changed using the `FromJsonIgnoreNull` annotation. 

If you add this annotation to a field the `null` values from a json will be ignored as if the key were missing in the json.


#### Serializing from ids 

Now we will define this entity:

```java
@Entity
@BoxfitClass
class Department {
    @BoxfitField
    @Id(assignable = true)
    long id;
    
    @BoxfitField
    ToMany<User> members;
}
```

And we get a JSON like this when retrieving a department:

```json
{ 
    "id": 1,
    "members": [1, 2, 3]
}
```

As you can see, `"members"` is not an array of `User` instances, but an array of numbers. **Boxfit** will recognize the numbers as being ids of users. If users with those ids are already in the local storage, they will be added as members of the department. If not, users with just the id property will be created an added. If later in the app a `User` with the same id is received, the members will be updated.

### Out of ObjectBox

Not only **ObjectBox** classes can be serialized with **Boxfit**. Any Java object can be imported with Boxfit if its class is properly annotated.

### Out of Retrofit

**Boxfit** can also be used out of **Retrofit** to convert `JSONObject` or `JSONArray` into Java objects:

```java
User user = boxfitSerializer.fromJson(User.class, myJsonObject);
List<Users> users = boxfitSerializer.fromJson(User.class, myJsonArray);
 
```

## To JSON

**Boxfit** can be also used to convert a `BoxfitClass` into a JSON.

You can convert bot a single object or a list of objects:

```java
User user = ...;
JSONObject jsonObject = boxfitSerializer.toJson(user);

List<User> users = ...;
JSONArray jsonArray = boxfitSerializer.toJson(users);
``` 

### To JSON Annotations
#### `ToJsonIgnore`
Add this annotation to a `BoxfitField` to ignore the field when writing a JSON. You can use this annotation to avoid infinite recursion:

```java
@Entity
@BoxfitClass
class User {
    @BoxfitField
    @Id(assignable = true)
    long id;
    
    @BoxfitField
    String name;
    
    @BoxfitField
    @ToJsonIgnore
    ToOne<Department> department;
}

@Entity
@BoxfitClass
class Department {
    @BoxfitField
    @Id(assignable = true)
    long id;
    
    @BoxfitField
    @Backlink
    ToMany<User> members;
}
```

If you don't add the `ToJsonIgnore` annotation, if you try to convert a department into a JSON, it would try to add the members. Then in the member, will try to add the department again, which will lead to an infinite loop.

#### `ToJsonIncludeNull`
By default, if a property is `null` when you try to get a JSON, the key for the field won't be included in the JSON. If you want to include the key even if the value is `null` you must annotate the field with `@ToJsonIncludeNull`.

#### `ToJsonAsId`
Add this annotation to a `BoxfitField` relationship to include its id instead of the full object when converting to json.

## Known issues
#### Lists must be initialized:
If you annotate a `List` field with `BoxfitField`, it must be initialized when the object is created, for instance:

```java
@Entity
@BoxfitClass
class Department {
    @BoxfitField
    @Id(assignable = true)
    long id;
    
    @BoxfitField
    List<User> members = new ArrayList();
}
```

This is not a problem with relationships declared as `ToMany` as they are automatically initialized. 
 
#### No generic classes:

JsonSerializable not allowed in generic classes, but allow it in its concrete subclasses. For instance, this will throw a compile error:

```java
@BoxfitClass
public class PaginatedResponse<T> {
    @BoxfitField
    int count;

    @BoxfitField
    int next;

    @BoxfitField
    int previous;

    @BoxfitField
    List<T> results = new ArrayList<>();
}
```

However, you can do this:

```java
public class PaginatedResponse<T> {
    @BoxfitField
    int count;

    @BoxfitField
    int next;

    @BoxfitField
    int previous;

    @BoxfitField
    List<T> results = new ArrayList<>();
}

@BoxfitClass
public static class PaginatedUsersResponse extends PaginatedResponse<User> {
}
```


## License

Boxfit is available under the [MIT license](LICENSE.md).

## Contact
[Manuel García-Estañ Martínez](http://github.com/ManueGE)  
[@manueGE](https://twitter.com/ManueGE)
