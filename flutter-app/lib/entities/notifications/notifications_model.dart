import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class Notifications {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'title')
  final String title;

  @JsonProperty(name: 'body')
  final String body;

  @JsonProperty(name: 'notificationDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime notificationDate;

  @JsonProperty(name: 'imageUrl')
  final String imageUrl;

  @JsonProperty(name: 'senderId')
  final int senderId;

  @JsonProperty(name: 'receiverId')
  final int receiverId;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const Notifications (
     this.id,
        this.title,
        this.body,
        this.notificationDate,
        this.imageUrl,
        this.senderId,
        this.receiverId,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
    );

@override
String toString() {
    return 'Notifications{'+
    'id: $id,' +
        'title: $title,' +
        'body: $body,' +
        'notificationDate: $notificationDate,' +
        'imageUrl: $imageUrl,' +
        'senderId: $senderId,' +
        'receiverId: $receiverId,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is Notifications &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


