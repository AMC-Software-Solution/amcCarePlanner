import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/client/client_model.dart';
import 'package:amcCarePlanner/entities/client/client_repository.dart';
import 'package:amcCarePlanner/entities/client/bloc/client_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'client_events.dart';
part 'client_state.dart';

class ClientBloc extends Bloc<ClientEvent, ClientState> {
  final ClientRepository _clientRepository;

  final clientNameController = TextEditingController();
  final clientDescriptionController = TextEditingController();
  final clientLogoUrlController = TextEditingController();
  final clientContactNameController = TextEditingController();
  final clientPhoneController = TextEditingController();
  final clientEmailController = TextEditingController();
  final createdDateController = TextEditingController();
  final reasonController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();

  ClientBloc({@required ClientRepository clientRepository}) : assert(clientRepository != null),
        _clientRepository = clientRepository, 
  super(ClientState(null,null,));

  @override
  void onTransition(Transition<ClientEvent, ClientState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ClientState> mapEventToState(ClientEvent event) async* {
    if (event is InitClientList) {
      yield* onInitList(event);
    } else if (event is ClientFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadClientByIdForEdit) {
      yield* onLoadClientIdForEdit(event);
    } else if (event is DeleteClientById) {
      yield* onDeleteClientId(event);
    } else if (event is LoadClientByIdForView) {
      yield* onLoadClientIdForView(event);
    }else if (event is ClientNameChanged){
      yield* onClientNameChange(event);
    }else if (event is ClientDescriptionChanged){
      yield* onClientDescriptionChange(event);
    }else if (event is ClientLogoUrlChanged){
      yield* onClientLogoUrlChange(event);
    }else if (event is ClientContactNameChanged){
      yield* onClientContactNameChange(event);
    }else if (event is ClientPhoneChanged){
      yield* onClientPhoneChange(event);
    }else if (event is ClientEmailChanged){
      yield* onClientEmailChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is EnabledChanged){
      yield* onEnabledChange(event);
    }else if (event is ReasonChanged){
      yield* onReasonChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ClientState> onInitList(InitClientList event) async* {
    yield this.state.copyWith(clientStatusUI: ClientStatusUI.loading);
    List<Client> clients = await _clientRepository.getAllClients();
    yield this.state.copyWith(clients: clients, clientStatusUI: ClientStatusUI.done);
  }

  Stream<ClientState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Client result;
        if(this.state.editMode) {
          Client newClient = Client(state.loadedClient.id,
            this.state.clientName.value,  
            this.state.clientDescription.value,  
            this.state.clientLogoUrl.value,  
            this.state.clientContactName.value,  
            this.state.clientPhone.value,  
            this.state.clientEmail.value,  
            this.state.createdDate.value,  
            this.state.enabled.value,  
            this.state.reason.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
          );

          result = await _clientRepository.update(newClient);
        } else {
          Client newClient = Client(null,
            this.state.clientName.value,  
            this.state.clientDescription.value,  
            this.state.clientLogoUrl.value,  
            this.state.clientContactName.value,  
            this.state.clientPhone.value,  
            this.state.clientEmail.value,  
            this.state.createdDate.value,  
            this.state.enabled.value,  
            this.state.reason.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
          );

          result = await _clientRepository.create(newClient);
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

  Stream<ClientState> onLoadClientIdForEdit(LoadClientByIdForEdit event) async* {
    yield this.state.copyWith(clientStatusUI: ClientStatusUI.loading);
    Client loadedClient = await _clientRepository.getClient(event.id);

    final clientName = ClientNameInput.dirty(loadedClient?.clientName != null ? loadedClient.clientName: '');
    final clientDescription = ClientDescriptionInput.dirty(loadedClient?.clientDescription != null ? loadedClient.clientDescription: '');
    final clientLogoUrl = ClientLogoUrlInput.dirty(loadedClient?.clientLogoUrl != null ? loadedClient.clientLogoUrl: '');
    final clientContactName = ClientContactNameInput.dirty(loadedClient?.clientContactName != null ? loadedClient.clientContactName: '');
    final clientPhone = ClientPhoneInput.dirty(loadedClient?.clientPhone != null ? loadedClient.clientPhone: '');
    final clientEmail = ClientEmailInput.dirty(loadedClient?.clientEmail != null ? loadedClient.clientEmail: '');
    final createdDate = CreatedDateInput.dirty(loadedClient?.createdDate != null ? loadedClient.createdDate: null);
    final enabled = EnabledInput.dirty(loadedClient?.enabled != null ? loadedClient.enabled: false);
    final reason = ReasonInput.dirty(loadedClient?.reason != null ? loadedClient.reason: '');
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedClient?.lastUpdatedDate != null ? loadedClient.lastUpdatedDate: null);
    final hasExtraData = HasExtraDataInput.dirty(loadedClient?.hasExtraData != null ? loadedClient.hasExtraData: false);

    yield this.state.copyWith(loadedClient: loadedClient, editMode: true,
      clientName: clientName,
      clientDescription: clientDescription,
      clientLogoUrl: clientLogoUrl,
      clientContactName: clientContactName,
      clientPhone: clientPhone,
      clientEmail: clientEmail,
      createdDate: createdDate,
      enabled: enabled,
      reason: reason,
      lastUpdatedDate: lastUpdatedDate,
      hasExtraData: hasExtraData,
    clientStatusUI: ClientStatusUI.done);

    clientNameController.text = loadedClient.clientName;
    clientDescriptionController.text = loadedClient.clientDescription;
    clientLogoUrlController.text = loadedClient.clientLogoUrl;
    clientContactNameController.text = loadedClient.clientContactName;
    clientPhoneController.text = loadedClient.clientPhone;
    clientEmailController.text = loadedClient.clientEmail;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedClient?.createdDate);
    reasonController.text = loadedClient.reason;
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedClient?.lastUpdatedDate);
  }

  Stream<ClientState> onDeleteClientId(DeleteClientById event) async* {
    try {
      await _clientRepository.delete(event.id);
      this.add(InitClientList());
      yield this.state.copyWith(deleteStatus: ClientDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ClientDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ClientDeleteStatus.none);
  }

  Stream<ClientState> onLoadClientIdForView(LoadClientByIdForView event) async* {
    yield this.state.copyWith(clientStatusUI: ClientStatusUI.loading);
    try {
      Client loadedClient = await _clientRepository.getClient(event.id);
      yield this.state.copyWith(loadedClient: loadedClient, clientStatusUI: ClientStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedClient: null, clientStatusUI: ClientStatusUI.error);
    }
  }


  Stream<ClientState> onClientNameChange(ClientNameChanged event) async* {
    final clientName = ClientNameInput.dirty(event.clientName);
    yield this.state.copyWith(
      clientName: clientName,
      formStatus: Formz.validate([
        clientName,
      this.state.clientDescription,
      this.state.clientLogoUrl,
      this.state.clientContactName,
      this.state.clientPhone,
      this.state.clientEmail,
      this.state.createdDate,
      this.state.enabled,
      this.state.reason,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onClientDescriptionChange(ClientDescriptionChanged event) async* {
    final clientDescription = ClientDescriptionInput.dirty(event.clientDescription);
    yield this.state.copyWith(
      clientDescription: clientDescription,
      formStatus: Formz.validate([
      this.state.clientName,
        clientDescription,
      this.state.clientLogoUrl,
      this.state.clientContactName,
      this.state.clientPhone,
      this.state.clientEmail,
      this.state.createdDate,
      this.state.enabled,
      this.state.reason,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onClientLogoUrlChange(ClientLogoUrlChanged event) async* {
    final clientLogoUrl = ClientLogoUrlInput.dirty(event.clientLogoUrl);
    yield this.state.copyWith(
      clientLogoUrl: clientLogoUrl,
      formStatus: Formz.validate([
      this.state.clientName,
      this.state.clientDescription,
        clientLogoUrl,
      this.state.clientContactName,
      this.state.clientPhone,
      this.state.clientEmail,
      this.state.createdDate,
      this.state.enabled,
      this.state.reason,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onClientContactNameChange(ClientContactNameChanged event) async* {
    final clientContactName = ClientContactNameInput.dirty(event.clientContactName);
    yield this.state.copyWith(
      clientContactName: clientContactName,
      formStatus: Formz.validate([
      this.state.clientName,
      this.state.clientDescription,
      this.state.clientLogoUrl,
        clientContactName,
      this.state.clientPhone,
      this.state.clientEmail,
      this.state.createdDate,
      this.state.enabled,
      this.state.reason,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onClientPhoneChange(ClientPhoneChanged event) async* {
    final clientPhone = ClientPhoneInput.dirty(event.clientPhone);
    yield this.state.copyWith(
      clientPhone: clientPhone,
      formStatus: Formz.validate([
      this.state.clientName,
      this.state.clientDescription,
      this.state.clientLogoUrl,
      this.state.clientContactName,
        clientPhone,
      this.state.clientEmail,
      this.state.createdDate,
      this.state.enabled,
      this.state.reason,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onClientEmailChange(ClientEmailChanged event) async* {
    final clientEmail = ClientEmailInput.dirty(event.clientEmail);
    yield this.state.copyWith(
      clientEmail: clientEmail,
      formStatus: Formz.validate([
      this.state.clientName,
      this.state.clientDescription,
      this.state.clientLogoUrl,
      this.state.clientContactName,
      this.state.clientPhone,
        clientEmail,
      this.state.createdDate,
      this.state.enabled,
      this.state.reason,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.clientName,
      this.state.clientDescription,
      this.state.clientLogoUrl,
      this.state.clientContactName,
      this.state.clientPhone,
      this.state.clientEmail,
        createdDate,
      this.state.enabled,
      this.state.reason,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onEnabledChange(EnabledChanged event) async* {
    final enabled = EnabledInput.dirty(event.enabled);
    yield this.state.copyWith(
      enabled: enabled,
      formStatus: Formz.validate([
      this.state.clientName,
      this.state.clientDescription,
      this.state.clientLogoUrl,
      this.state.clientContactName,
      this.state.clientPhone,
      this.state.clientEmail,
      this.state.createdDate,
        enabled,
      this.state.reason,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onReasonChange(ReasonChanged event) async* {
    final reason = ReasonInput.dirty(event.reason);
    yield this.state.copyWith(
      reason: reason,
      formStatus: Formz.validate([
      this.state.clientName,
      this.state.clientDescription,
      this.state.clientLogoUrl,
      this.state.clientContactName,
      this.state.clientPhone,
      this.state.clientEmail,
      this.state.createdDate,
      this.state.enabled,
        reason,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.clientName,
      this.state.clientDescription,
      this.state.clientLogoUrl,
      this.state.clientContactName,
      this.state.clientPhone,
      this.state.clientEmail,
      this.state.createdDate,
      this.state.enabled,
      this.state.reason,
        lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ClientState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.clientName,
      this.state.clientDescription,
      this.state.clientLogoUrl,
      this.state.clientContactName,
      this.state.clientPhone,
      this.state.clientEmail,
      this.state.createdDate,
      this.state.enabled,
      this.state.reason,
      this.state.lastUpdatedDate,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    clientNameController.dispose();
    clientDescriptionController.dispose();
    clientLogoUrlController.dispose();
    clientContactNameController.dispose();
    clientPhoneController.dispose();
    clientEmailController.dispose();
    createdDateController.dispose();
    reasonController.dispose();
    lastUpdatedDateController.dispose();
    return super.close();
  }

}