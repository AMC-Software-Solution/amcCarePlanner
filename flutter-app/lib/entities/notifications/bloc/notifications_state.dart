part of 'notifications_bloc.dart';

enum NotificationsStatusUI {init, loading, error, done}
enum NotificationsDeleteStatus {ok, ko, none}

class NotificationsState extends Equatable {
  final List<Notifications> notifications;
  final Notifications loadedNotifications;
  final bool editMode;
  final NotificationsDeleteStatus deleteStatus;
  final NotificationsStatusUI notificationsStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final TitleInput title;
  final BodyInput body;
  final NotificationDateInput notificationDate;
  final ImageUrlInput imageUrl;
  final SenderIdInput senderId;
  final ReceiverIdInput receiverId;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  NotificationsState(
NotificationDateInput notificationDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.notifications = const [],
    this.notificationsStatusUI = NotificationsStatusUI.init,
    this.loadedNotifications = const Notifications(0,'','',null,'',0,0,null,null,0,false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = NotificationsDeleteStatus.none,
    this.title = const TitleInput.pure(),
    this.body = const BodyInput.pure(),
    this.imageUrl = const ImageUrlInput.pure(),
    this.senderId = const SenderIdInput.pure(),
    this.receiverId = const ReceiverIdInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.notificationDate = notificationDate ?? NotificationDateInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  NotificationsState copyWith({
    List<Notifications> notifications,
    NotificationsStatusUI notificationsStatusUI,
    bool editMode,
    NotificationsDeleteStatus deleteStatus,
    Notifications loadedNotifications,
    FormzStatus formStatus,
    String generalNotificationKey,
    TitleInput title,
    BodyInput body,
    NotificationDateInput notificationDate,
    ImageUrlInput imageUrl,
    SenderIdInput senderId,
    ReceiverIdInput receiverId,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return NotificationsState(
        notificationDate,
        createdDate,
        lastUpdatedDate,
      notifications: notifications ?? this.notifications,
      notificationsStatusUI: notificationsStatusUI ?? this.notificationsStatusUI,
      loadedNotifications: loadedNotifications ?? this.loadedNotifications,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      title: title ?? this.title,
      body: body ?? this.body,
      imageUrl: imageUrl ?? this.imageUrl,
      senderId: senderId ?? this.senderId,
      receiverId: receiverId ?? this.receiverId,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [notifications, notificationsStatusUI,
     loadedNotifications, editMode, deleteStatus, formStatus, generalNotificationKey, 
title,body,notificationDate,imageUrl,senderId,receiverId,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
