# distributed-cache

Design a key-value store that can perform the following
Functional requirements
●	 Store a set of attributes (value) against a particular key (sk)
●	Stretch - Perform a secondary index scan to fetch all key along with their
attributes where one of the attribute values is v.
●	Fetch the value stored against a particular key (k)
●	 Delete a key (k)

Example:

![image](https://user-images.githubusercontent.com/9547856/158347739-3d37fe13-46d7-4458-9bb8-65ab8ff75353.png)


Key can have a value consisting of multiple attributes.
Each attribute will have name, type associated (primitive types - boolean, double, integer, string) & type has to be identified at run time.
1)  Key = delhi has 2 attributes only ( pollution_level & population)
2)  Key = jakarta has 3 attributes (latitude, longitude, pollution_level)
3)  Key = bangalore has 4 attributes (extra - free_food)
4)  Key = india has 2 attributes (capital & population)34w
5)  Key = crocin has 2 attributes (category & manufacturer)
Example of Secondary index:
●	 Get all keys (cities) where pollution_level is high, it will return delhi , jakarta
●	 Get all medicines by manufacturer (GSK) , it will return Crocin 

Attribute
1.	Attribute is uniquely identified by its name (latitude, longitude etc.)
2.	Once data type is associated with a particular attribute, it cannot be changed.
(i.e. free_food when defined takes type = boolean, hence, any key when using the attribute - free_food must allow only boolean values on subsequent inserts/updates)



Notes

●  Preferable Language - Java
●  Follow standard OOPs concept and best practices in the industry
●  code should be extensible and use design pattern and design principles
● use proper naming conventions
●  Make sure code is thread safe.
●  what design pattern used.
●  Make use of generics .--
●  Stub the methods for complex logic with comments supporting your
assumptions.
●  Write at least 1 test case for each functionality.

Case-1


Key:[ BBSR  : {{ “temperature”:20.0} ,{ “latitude”:”20.5”}}] allowed
Key:[ bangalore  : {{ “temperature”:true}}] - not allowed should throws exception 
Key:[ Chennai  : {{ “temperature”:20.0} ,{ “latitude”:”30.5”} ,{“population”:1000000 } }] allowed
Key:[ Mombay  : {{ “temperature”:20.0} ,{ “latitude”:”30.5”} ,{“population”:”1 million” } }] not allowed
Key:[ Siddhanta  : {{ “length”:20.0} ,{ “width”:”30.5”} ,{“height”:”30.5” } }]


Case -2
Sec index- population 
Make sec index as population_level

Query 

population_level-> High - {‘delhi’,’jakarta’}


Population -> 1 million  -{ India }

Sec index population_level 
population_level-> High - {‘delhi’,’jakarta’}


