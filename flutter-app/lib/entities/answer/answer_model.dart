import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../question/question_model.dart';
import '../service_user/service_user_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class Answer {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'answer')
  final String answer;

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

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'question')
  final Question question;

  @JsonProperty(name: 'serviceUser')
  final ServiceUser serviceUser;

  @JsonProperty(name: 'recordedBy')
  final Employee recordedBy;

 const Answer (
     this.id,
        this.answer,
        this.description,
        this.attribute1,
        this.attribute2,
        this.attribute3,
        this.attribute4,
        this.attribute5,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.question,
        this.serviceUser,
        this.recordedBy,
    );

@override
String toString() {
    return 'Answer{'+
    'id: $id,' +
        'answer: $answer,' +
        'description: $description,' +
        'attribute1: $attribute1,' +
        'attribute2: $attribute2,' +
        'attribute3: $attribute3,' +
        'attribute4: $attribute4,' +
        'attribute5: $attribute5,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'question: $question,' +
        'serviceUser: $serviceUser,' +
        'recordedBy: $recordedBy,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is Answer &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


