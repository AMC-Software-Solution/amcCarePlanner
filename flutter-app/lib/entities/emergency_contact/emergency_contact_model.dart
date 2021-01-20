import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../service_user/service_user_model.dart';

@jsonSerializable
class EmergencyContact {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'name')
  final String name;

  @JsonProperty(name: 'contactRelationship')
  final String contactRelationship;

  @JsonProperty(name: 'isKeyHolder')
  final bool isKeyHolder;

  @JsonProperty(name: 'infoSharingConsentGiven')
  final bool infoSharingConsentGiven;

  @JsonProperty(name: 'preferredContactNumber')
  final String preferredContactNumber;

  @JsonProperty(name: 'fullAddress')
  final String fullAddress;

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

 const EmergencyContact (
     this.id,
        this.name,
        this.contactRelationship,
        this.isKeyHolder,
        this.infoSharingConsentGiven,
        this.preferredContactNumber,
        this.fullAddress,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.serviceUser,
    );

@override
String toString() {
    return 'EmergencyContact{'+
    'id: $id,' +
        'name: $name,' +
        'contactRelationship: $contactRelationship,' +
        'isKeyHolder: $isKeyHolder,' +
        'infoSharingConsentGiven: $infoSharingConsentGiven,' +
        'preferredContactNumber: $preferredContactNumber,' +
        'fullAddress: $fullAddress,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'serviceUser: $serviceUser,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is EmergencyContact &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


