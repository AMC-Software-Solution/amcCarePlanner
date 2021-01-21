import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../country/country_model.dart';
import '../service_user/service_user_model.dart';

@jsonSerializable
class Equality {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'gender', enumValues: ServiceUserGender.values)
  final ServiceUserGender gender;

  @JsonProperty(name: 'maritalStatus', enumValues: MaritalStatus.values)
  final MaritalStatus maritalStatus;

  @JsonProperty(name: 'religion', enumValues: Religion.values)
  final Religion religion;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'nationality')
  final Country nationality;

  @JsonProperty(name: 'serviceUser')
  final ServiceUser serviceUser;

 const Equality (
     this.id,
        this.gender,
        this.maritalStatus,
        this.religion,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.nationality,
        this.serviceUser,
    );

@override
String toString() {
    return 'Equality{'+
    'id: $id,' +
        'gender: $gender,' +
        'maritalStatus: $maritalStatus,' +
        'religion: $religion,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'nationality: $nationality,' +
        'serviceUser: $serviceUser,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is Equality &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: ServiceUserGender.values)
enum ServiceUserGender {
    MALE ,
    FEMALE ,
    OTHER
}@jsonSerializable
@Json(enumValues: MaritalStatus.values)
enum MaritalStatus {
    MARRIED ,
    SINGLE ,
    DIVORCED ,
    WIDOWED ,
    OTHER
}@jsonSerializable
@Json(enumValues: Religion.values)
enum Religion {
    MUSLIM ,
    CHRISTIANITY ,
    HINDU ,
    ATHEIST ,
    JEWISH ,
    OTHER
}
