import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class DisabilityType {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'disability')
  final String disability;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const DisabilityType (
     this.id,
        this.disability,
        this.description,
        this.hasExtraData,
    );

@override
String toString() {
    return 'DisabilityType{'+
    'id: $id,' +
        'disability: $disability,' +
        'description: $description,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is DisabilityType &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


