import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../service_user/service_user_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class PowerOfAttorney {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'powerOfAttorneyConsent')
  final bool powerOfAttorneyConsent;

  @JsonProperty(name: 'healthAndWelfare')
  final bool healthAndWelfare;

  @JsonProperty(name: 'healthAndWelfareName')
  final String healthAndWelfareName;

  @JsonProperty(name: 'propertyAndFinAffairs')
  final bool propertyAndFinAffairs;

  @JsonProperty(name: 'propertyAndFinAffairsName')
  final String propertyAndFinAffairsName;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'serviceUser')
  final ServiceUser serviceUser;

  @JsonProperty(name: 'witnessedBy')
  final Employee witnessedBy;

 const PowerOfAttorney (
     this.id,
        this.powerOfAttorneyConsent,
        this.healthAndWelfare,
        this.healthAndWelfareName,
        this.propertyAndFinAffairs,
        this.propertyAndFinAffairsName,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.serviceUser,
        this.witnessedBy,
    );

@override
String toString() {
    return 'PowerOfAttorney{'+
    'id: $id,' +
        'powerOfAttorneyConsent: $powerOfAttorneyConsent,' +
        'healthAndWelfare: $healthAndWelfare,' +
        'healthAndWelfareName: $healthAndWelfareName,' +
        'propertyAndFinAffairs: $propertyAndFinAffairs,' +
        'propertyAndFinAffairsName: $propertyAndFinAffairsName,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'serviceUser: $serviceUser,' +
        'witnessedBy: $witnessedBy,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is PowerOfAttorney &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


