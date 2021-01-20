import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/notifications/notifications_model.dart';
import 'package:amcCarePlanner/entities/notifications/notifications_repository.dart';
import 'package:amcCarePlanner/entities/notifications/bloc/notifications_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'notifications_events.dart';
part 'notifications_state.dart';

class NotificationsBloc extends Bloc<NotificationsEvent, NotificationsState> {
  final NotificationsRepository _notificationsRepository;

  final titleController = TextEditingController();
  final bodyController = TextEditingController();
  final notificationDateController = TextEditingController();
  final imageUrlController = TextEditingController();
  final senderIdController = TextEditingController();
  final receiverIdController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  NotificationsBloc({@required NotificationsRepository notificationsRepository}) : assert(notificationsRepository != null),
        _notificationsRepository = notificationsRepository, 
  super(NotificationsState(null,null,null,));

  @override
  void onTransition(Transition<NotificationsEvent, NotificationsState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<NotificationsState> mapEventToState(NotificationsEvent event) async* {
    if (event is InitNotificationsList) {
      yield* onInitList(event);
    } else if (event is NotificationsFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadNotificationsByIdForEdit) {
      yield* onLoadNotificationsIdForEdit(event);
    } else if (event is DeleteNotificationsById) {
      yield* onDeleteNotificationsId(event);
    } else if (event is LoadNotificationsByIdForView) {
      yield* onLoadNotificationsIdForView(event);
    }else if (event is TitleChanged){
      yield* onTitleChange(event);
    }else if (event is BodyChanged){
      yield* onBodyChange(event);
    }else if (event is NotificationDateChanged){
      yield* onNotificationDateChange(event);
    }else if (event is ImageUrlChanged){
      yield* onImageUrlChange(event);
    }else if (event is SenderIdChanged){
      yield* onSenderIdChange(event);
    }else if (event is ReceiverIdChanged){
      yield* onReceiverIdChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<NotificationsState> onInitList(InitNotificationsList event) async* {
    yield this.state.copyWith(notificationsStatusUI: NotificationsStatusUI.loading);
    List<Notifications> notifications = await _notificationsRepository.getAllNotifications();
    yield this.state.copyWith(notifications: notifications, notificationsStatusUI: NotificationsStatusUI.done);
  }

  Stream<NotificationsState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Notifications result;
        if(this.state.editMode) {
          Notifications newNotifications = Notifications(state.loadedNotifications.id,
            this.state.title.value,  
            this.state.body.value,  
            this.state.notificationDate.value,  
            this.state.imageUrl.value,  
            this.state.senderId.value,  
            this.state.receiverId.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _notificationsRepository.update(newNotifications);
        } else {
          Notifications newNotifications = Notifications(null,
            this.state.title.value,  
            this.state.body.value,  
            this.state.notificationDate.value,  
            this.state.imageUrl.value,  
            this.state.senderId.value,  
            this.state.receiverId.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _notificationsRepository.create(newNotifications);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<NotificationsState> onLoadNotificationsIdForEdit(LoadNotificationsByIdForEdit event) async* {
    yield this.state.copyWith(notificationsStatusUI: NotificationsStatusUI.loading);
    Notifications loadedNotifications = await _notificationsRepository.getNotifications(event.id);

    final title = TitleInput.dirty(loadedNotifications?.title != null ? loadedNotifications.title: '');
    final body = BodyInput.dirty(loadedNotifications?.body != null ? loadedNotifications.body: '');
    final notificationDate = NotificationDateInput.dirty(loadedNotifications?.notificationDate != null ? loadedNotifications.notificationDate: null);
    final imageUrl = ImageUrlInput.dirty(loadedNotifications?.imageUrl != null ? loadedNotifications.imageUrl: '');
    final senderId = SenderIdInput.dirty(loadedNotifications?.senderId != null ? loadedNotifications.senderId: 0);
    final receiverId = ReceiverIdInput.dirty(loadedNotifications?.receiverId != null ? loadedNotifications.receiverId: 0);
    final createdDate = CreatedDateInput.dirty(loadedNotifications?.createdDate != null ? loadedNotifications.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedNotifications?.lastUpdatedDate != null ? loadedNotifications.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedNotifications?.clientId != null ? loadedNotifications.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedNotifications?.hasExtraData != null ? loadedNotifications.hasExtraData: false);

    yield this.state.copyWith(loadedNotifications: loadedNotifications, editMode: true,
      title: title,
      body: body,
      notificationDate: notificationDate,
      imageUrl: imageUrl,
      senderId: senderId,
      receiverId: receiverId,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    notificationsStatusUI: NotificationsStatusUI.done);

    titleController.text = loadedNotifications.title;
    bodyController.text = loadedNotifications.body;
    notificationDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedNotifications?.notificationDate);
    imageUrlController.text = loadedNotifications.imageUrl;
    senderIdController.text = loadedNotifications.senderId.toString();
    receiverIdController.text = loadedNotifications.receiverId.toString();
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedNotifications?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedNotifications?.lastUpdatedDate);
    clientIdController.text = loadedNotifications.clientId.toString();
  }

  Stream<NotificationsState> onDeleteNotificationsId(DeleteNotificationsById event) async* {
    try {
      await _notificationsRepository.delete(event.id);
      this.add(InitNotificationsList());
      yield this.state.copyWith(deleteStatus: NotificationsDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: NotificationsDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: NotificationsDeleteStatus.none);
  }

  Stream<NotificationsState> onLoadNotificationsIdForView(LoadNotificationsByIdForView event) async* {
    yield this.state.copyWith(notificationsStatusUI: NotificationsStatusUI.loading);
    try {
      Notifications loadedNotifications = await _notificationsRepository.getNotifications(event.id);
      yield this.state.copyWith(loadedNotifications: loadedNotifications, notificationsStatusUI: NotificationsStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedNotifications: null, notificationsStatusUI: NotificationsStatusUI.error);
    }
  }


  Stream<NotificationsState> onTitleChange(TitleChanged event) async* {
    final title = TitleInput.dirty(event.title);
    yield this.state.copyWith(
      title: title,
      formStatus: Formz.validate([
        title,
      this.state.body,
      this.state.notificationDate,
      this.state.imageUrl,
      this.state.senderId,
      this.state.receiverId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<NotificationsState> onBodyChange(BodyChanged event) async* {
    final body = BodyInput.dirty(event.body);
    yield this.state.copyWith(
      body: body,
      formStatus: Formz.validate([
      this.state.title,
        body,
      this.state.notificationDate,
      this.state.imageUrl,
      this.state.senderId,
      this.state.receiverId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<NotificationsState> onNotificationDateChange(NotificationDateChanged event) async* {
    final notificationDate = NotificationDateInput.dirty(event.notificationDate);
    yield this.state.copyWith(
      notificationDate: notificationDate,
      formStatus: Formz.validate([
      this.state.title,
      this.state.body,
        notificationDate,
      this.state.imageUrl,
      this.state.senderId,
      this.state.receiverId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<NotificationsState> onImageUrlChange(ImageUrlChanged event) async* {
    final imageUrl = ImageUrlInput.dirty(event.imageUrl);
    yield this.state.copyWith(
      imageUrl: imageUrl,
      formStatus: Formz.validate([
      this.state.title,
      this.state.body,
      this.state.notificationDate,
        imageUrl,
      this.state.senderId,
      this.state.receiverId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<NotificationsState> onSenderIdChange(SenderIdChanged event) async* {
    final senderId = SenderIdInput.dirty(event.senderId);
    yield this.state.copyWith(
      senderId: senderId,
      formStatus: Formz.validate([
      this.state.title,
      this.state.body,
      this.state.notificationDate,
      this.state.imageUrl,
        senderId,
      this.state.receiverId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<NotificationsState> onReceiverIdChange(ReceiverIdChanged event) async* {
    final receiverId = ReceiverIdInput.dirty(event.receiverId);
    yield this.state.copyWith(
      receiverId: receiverId,
      formStatus: Formz.validate([
      this.state.title,
      this.state.body,
      this.state.notificationDate,
      this.state.imageUrl,
      this.state.senderId,
        receiverId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<NotificationsState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.title,
      this.state.body,
      this.state.notificationDate,
      this.state.imageUrl,
      this.state.senderId,
      this.state.receiverId,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<NotificationsState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.title,
      this.state.body,
      this.state.notificationDate,
      this.state.imageUrl,
      this.state.senderId,
      this.state.receiverId,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<NotificationsState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.title,
      this.state.body,
      this.state.notificationDate,
      this.state.imageUrl,
      this.state.senderId,
      this.state.receiverId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<NotificationsState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.title,
      this.state.body,
      this.state.notificationDate,
      this.state.imageUrl,
      this.state.senderId,
      this.state.receiverId,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    titleController.dispose();
    bodyController.dispose();
    notificationDateController.dispose();
    imageUrlController.dispose();
    senderIdController.dispose();
    receiverIdController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}