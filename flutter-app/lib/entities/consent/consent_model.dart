import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../service_user/service_user_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class Consent {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'title')
  final String title;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'giveConsent')
  final bool giveConsent;

  @JsonProperty(name: 'arrangements')
  final String arrangements;

  @JsonProperty(name: 'serviceUserSignature')
  final String serviceUserSignature;

  @JsonProperty(name: 'signatureImageUrl')
  final String signatureImageUrl;

  @JsonProperty(name: 'consentDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime consentDate;

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

 const Consent (
     this.id,
        this.title,
        this.description,
        this.giveConsent,
        this.arrangements,
        this.serviceUserSignature,
        this.signatureImageUrl,
        this.consentDate,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.serviceUser,
        this.witnessedBy,
    );

@override
String toString() {
    return 'Consent{'+
    'id: $id,' +
        'title: $title,' +
        'description: $description,' +
        'giveConsent: $giveConsent,' +
        'arrangements: $arrangements,' +
        'serviceUserSignature: $serviceUserSignature,' +
        'signatureImageUrl: $signatureImageUrl,' +
        'consentDate: $consentDate,' +
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
    other is Consent &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


