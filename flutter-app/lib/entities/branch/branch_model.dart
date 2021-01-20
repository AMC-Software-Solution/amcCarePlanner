import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../client/client_model.dart';

@jsonSerializable
class Branch {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'name')
  final String name;

  @JsonProperty(name: 'address')
  final String address;

  @JsonProperty(name: 'telephone')
  final String telephone;

  @JsonProperty(name: 'contactName')
  final String contactName;

  @JsonProperty(name: 'branchEmail')
  final String branchEmail;

  @JsonProperty(name: 'photoUrl')
  final String photoUrl;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'client')
  final Client client;
        
 const Branch (
     this.id,
        this.name,
        this.address,
        this.telephone,
        this.contactName,
        this.branchEmail,
        this.photoUrl,
        this.createdDate,
        this.lastUpdatedDate,
        this.hasExtraData,
        this.client,
    );

@override
String toString() {
    return 'Branch{'+
    'id: $id,' +
        'name: $name,' +
        'address: $address,' +
        'telephone: $telephone,' +
        'contactName: $contactName,' +
        'branchEmail: $branchEmail,' +
        'photoUrl: $photoUrl,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'hasExtraData: $hasExtraData,' +
        'client: $client,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is Branch &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


