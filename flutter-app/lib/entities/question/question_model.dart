import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class Question {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'question')
  final String question;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'attribute1')
  final String attribute1;

  @JsonProperty(name: 'attribute2')
  final String attribute2;

  @JsonProperty(name: 'attribute3')
  final String attribute3;

  @JsonProperty(name: 'attribute4')
  final String attribute4;

  @JsonProperty(name: 'attribute5')
  final String attribute5;

  @JsonProperty(name: 'optional')
  final bool optional;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const Question (
     this.id,
        this.question,
        this.description,
        this.attribute1,
        this.attribute2,
        this.attribute3,
        this.attribute4,
        this.attribute5,
        this.optional,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
    );

@override
String toString() {
    return 'Question{'+
    'id: $id,' +
        'question: $question,' +
        'description: $description,' +
        'attribute1: $attribute1,' +
        'attribute2: $attribute2,' +
        'attribute3: $attribute3,' +
        'attribute4: $attribute4,' +
        'attribute5: $attribute5,' +
        'optional: $optional,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is Question &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


