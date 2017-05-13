# RewardService
This service is responsible for randomly generating a reward for a player (right now this is always a gadget)

## Endpoints

### /categories/{categoryId}/rewards/{id} [GET]:
#### Gets more information about a specific reward

**Response**:
```json
{
"type": "GADGET",
"rarity": "COMMON",
"name": "Earth Shake Gadget",
"subcategory": "physical",
"description": ["Straight from Avatar,", "this gives you the ability", "to §ashake the earth§r and", "knock people to space!"],
"item": {
  "material": "PISTON_BASE"
},
"spec": {
  "type": "consumable",
  "id": "EARTH_SHAKE_GADGET"
}}
```
In this case the spec.id specifies the gadget id and spec.type the gadget type

### /categories/{categoryId}/rewards/{id} [PUT]:
#### Adds/updates a reward
States: NONE, PLAYERS_10, PLAYERS_25, PLAYERS_50, PLAYERS_100, ALL



**Request**:
```json
{

"type": "GADGET",
"rarity": "COMMON",
"name": "Earth Shake Gadget",
"description": ["Straight from Avatar,", "this gives you the ability", "to §ashake the earth§r and", "knock people to space!"],
"subcategory": "physical",
"item": {
  "material": "PISTON_BASE"
},
"spec": {
  "type": "consumable",
  "id": "EARTH_SHAKE_GADGET"
}}
```

Type 'currency' spec:
```json
{
"spec": {
  "currency": "GOLD",
  "amount": 512
}}
```

**Response**:
```json
{
"success": true
}
```

### /players/{uuid}/reward?categoryId=basic&quality=5 [POST]:
#### Grants a random reward to the specified player
The quality represents the chance of getting better rewards (0-5), higher is better.


**Response**:
```json
{
"success": true,
"categoryId": "basic",
"type": "gadget",
"hasAlready": false,
"spec": {
  "id": "EARTH_SHAKE",
  "gadgetUuid": "afee28ff-6b5f-42b3-9776-10694b381d48"
}}
```

```json
{
"success": true,
"categoryId": "basic",
"hasAlready": false,
"type": "currency",
"spec": {
  "currency": "GOLD",
  "amount": 512
}}
```

## Environment
| Name | Value |
| --------- | --- |
| MONGO_URI | {mongo_uri} |
| DB_NAME | {db name to store data} |
| GADGETS_SERVICE_ADDRESS | http://gadgetsservice:8080 |