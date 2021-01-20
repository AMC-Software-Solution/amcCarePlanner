part of 'notifications_bloc.dart';

abstract class NotificationsEvent extends Equatable {
  const NotificationsEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitNotificationsList extends NotificationsEvent {}

class TitleChanged extends NotificationsEvent {
  final String title;
  
  const TitleChanged({@required this.title});
  
  @override
  List<Object> get props => [title];
}
class BodyChanged extends NotificationsEvent {
  final String body;
  
  const BodyChanged({@required this.body});
  
  @override
  List<Object> get props => [body];
}
class NotificationDateChanged extends NotificationsEvent {
  final DateTime notificationDate;
  
  const NotificationDateChanged({@required this.notificationDate});
  
  @override
  List<Object> get props => [notificationDate];
}
class ImageUrlChanged extends NotificationsEvent {
  final String imageUrl;
  
  const ImageUrlChanged({@required this.imageUrl});
  
  @override
  List<Object> get props => [imageUrl];
}
class SenderIdChanged extends NotificationsEvent {
  final int senderId;
  
  const SenderIdChanged({@required this.senderId});
  
  @override
  List<Object> get props => [senderId];
}
class ReceiverIdChanged extends NotificationsEvent {
  final int receiverId;
  
  const ReceiverIdChanged({@required this.receiverId});
  
  @override
  List<Object> get props => [receiverId];
}
class CreatedDateChanged extends NotificationsEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends NotificationsEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends NotificationsEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends NotificationsEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class NotificationsFormSubmitted extends NotificationsEvent {}

class LoadNotificationsByIdForEdit extends NotificationsEvent {
  final int id;

  const LoadNotificationsByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteNotificationsById extends NotificationsEvent {
  final int id;

  const DeleteNotificationsById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadNotificationsByIdForView extends NotificationsEvent {
  final int id;

  const LoadNotificationsByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
