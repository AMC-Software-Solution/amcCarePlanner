import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class RelationshipType {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'relationType')
  final String relationType;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const RelationshipType (
     this.id,
        this.relationType,
        this.description,
        this.clientId,
        this.hasExtraData,
    );

@override
String toString() {
    return 'RelationshipType{'+
    'id: $id,' +
        'relationType: $relationType,' +
        'description: $description,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is RelationshipType &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


