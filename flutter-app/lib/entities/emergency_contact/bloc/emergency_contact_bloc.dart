import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/emergency_contact/emergency_contact_model.dart';
import 'package:amcCarePlanner/entities/emergency_contact/emergency_contact_repository.dart';
import 'package:amcCarePlanner/entities/emergency_contact/bloc/emergency_contact_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'emergency_contact_events.dart';
part 'emergency_contact_state.dart';

class EmergencyContactBloc extends Bloc<EmergencyContactEvent, EmergencyContactState> {
  final EmergencyContactRepository _emergencyContactRepository;

  final nameController = TextEditingController();
  final contactRelationshipController = TextEditingController();
  final preferredContactNumberController = TextEditingController();
  final fullAddressController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  EmergencyContactBloc({@required EmergencyContactRepository emergencyContactRepository}) : assert(emergencyContactRepository != null),
        _emergencyContactRepository = emergencyContactRepository, 
  super(EmergencyContactState(null,null,));

  @override
  void onTransition(Transition<EmergencyContactEvent, EmergencyContactState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EmergencyContactState> mapEventToState(EmergencyContactEvent event) async* {
    if (event is InitEmergencyContactList) {
      yield* onInitList(event);
    } else if (event is EmergencyContactFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEmergencyContactByIdForEdit) {
      yield* onLoadEmergencyContactIdForEdit(event);
    } else if (event is DeleteEmergencyContactById) {
      yield* onDeleteEmergencyContactId(event);
    } else if (event is LoadEmergencyContactByIdForView) {
      yield* onLoadEmergencyContactIdForView(event);
    }else if (event is NameChanged){
      yield* onNameChange(event);
    }else if (event is ContactRelationshipChanged){
      yield* onContactRelationshipChange(event);
    }else if (event is IsKeyHolderChanged){
      yield* onIsKeyHolderChange(event);
    }else if (event is InfoSharingConsentGivenChanged){
      yield* onInfoSharingConsentGivenChange(event);
    }else if (event is PreferredContactNumberChanged){
      yield* onPreferredContactNumberChange(event);
    }else if (event is FullAddressChanged){
      yield* onFullAddressChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<EmergencyContactState> onInitList(InitEmergencyContactList event) async* {
    yield this.state.copyWith(emergencyContactStatusUI: EmergencyContactStatusUI.loading);
    List<EmergencyContact> emergencyContacts = await _emergencyContactRepository.getAllEmergencyContacts();
    yield this.state.copyWith(emergencyContacts: emergencyContacts, emergencyContactStatusUI: EmergencyContactStatusUI.done);
  }

  Stream<EmergencyContactState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        EmergencyContact result;
        if(this.state.editMode) {
          EmergencyContact newEmergencyContact = EmergencyContact(state.loadedEmergencyContact.id,
            this.state.name.value,  
            this.state.contactRelationship.value,  
            this.state.isKeyHolder.value,  
            this.state.infoSharingConsentGiven.value,  
            this.state.preferredContactNumber.value,  
            this.state.fullAddress.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _emergencyContactRepository.update(newEmergencyContact);
        } else {
          EmergencyContact newEmergencyContact = EmergencyContact(null,
            this.state.name.value,  
            this.state.contactRelationship.value,  
            this.state.isKeyHolder.value,  
            this.state.infoSharingConsentGiven.value,  
            this.state.preferredContactNumber.value,  
            this.state.fullAddress.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _emergencyContactRepository.create(newEmergencyContact);
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

  Stream<EmergencyContactState> onLoadEmergencyContactIdForEdit(LoadEmergencyContactByIdForEdit event) async* {
    yield this.state.copyWith(emergencyContactStatusUI: EmergencyContactStatusUI.loading);
    EmergencyContact loadedEmergencyContact = await _emergencyContactRepository.getEmergencyContact(event.id);

    final name = NameInput.dirty(loadedEmergencyContact?.name != null ? loadedEmergencyContact.name: '');
    final contactRelationship = ContactRelationshipInput.dirty(loadedEmergencyContact?.contactRelationship != null ? loadedEmergencyContact.contactRelationship: '');
    final isKeyHolder = IsKeyHolderInput.dirty(loadedEmergencyContact?.isKeyHolder != null ? loadedEmergencyContact.isKeyHolder: false);
    final infoSharingConsentGiven = InfoSharingConsentGivenInput.dirty(loadedEmergencyContact?.infoSharingConsentGiven != null ? loadedEmergencyContact.infoSharingConsentGiven: false);
    final preferredContactNumber = PreferredContactNumberInput.dirty(loadedEmergencyContact?.preferredContactNumber != null ? loadedEmergencyContact.preferredContactNumber: '');
    final fullAddress = FullAddressInput.dirty(loadedEmergencyContact?.fullAddress != null ? loadedEmergencyContact.fullAddress: '');
    final createdDate = CreatedDateInput.dirty(loadedEmergencyContact?.createdDate != null ? loadedEmergencyContact.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedEmergencyContact?.lastUpdatedDate != null ? loadedEmergencyContact.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedEmergencyContact?.clientId != null ? loadedEmergencyContact.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedEmergencyContact?.hasExtraData != null ? loadedEmergencyContact.hasExtraData: false);

    yield this.state.copyWith(loadedEmergencyContact: loadedEmergencyContact, editMode: true,
      name: name,
      contactRelationship: contactRelationship,
      isKeyHolder: isKeyHolder,
      infoSharingConsentGiven: infoSharingConsentGiven,
      preferredContactNumber: preferredContactNumber,
      fullAddress: fullAddress,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    emergencyContactStatusUI: EmergencyContactStatusUI.done);

    nameController.text = loadedEmergencyContact.name;
    contactRelationshipController.text = loadedEmergencyContact.contactRelationship;
    preferredContactNumberController.text = loadedEmergencyContact.preferredContactNumber;
    fullAddressController.text = loadedEmergencyContact.fullAddress;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmergencyContact?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmergencyContact?.lastUpdatedDate);
    clientIdController.text = loadedEmergencyContact.clientId.toString();
  }

  Stream<EmergencyContactState> onDeleteEmergencyContactId(DeleteEmergencyContactById event) async* {
    try {
      await _emergencyContactRepository.delete(event.id);
      this.add(InitEmergencyContactList());
      yield this.state.copyWith(deleteStatus: EmergencyContactDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EmergencyContactDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EmergencyContactDeleteStatus.none);
  }

  Stream<EmergencyContactState> onLoadEmergencyContactIdForView(LoadEmergencyContactByIdForView event) async* {
    yield this.state.copyWith(emergencyContactStatusUI: EmergencyContactStatusUI.loading);
    try {
      EmergencyContact loadedEmergencyContact = await _emergencyContactRepository.getEmergencyContact(event.id);
      yield this.state.copyWith(loadedEmergencyContact: loadedEmergencyContact, emergencyContactStatusUI: EmergencyContactStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEmergencyContact: null, emergencyContactStatusUI: EmergencyContactStatusUI.error);
    }
  }


  Stream<EmergencyContactState> onNameChange(NameChanged event) async* {
    final name = NameInput.dirty(event.name);
    yield this.state.copyWith(
      name: name,
      formStatus: Formz.validate([
        name,
      this.state.contactRelationship,
      this.state.isKeyHolder,
      this.state.infoSharingConsentGiven,
      this.state.preferredContactNumber,
      this.state.fullAddress,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmergencyContactState> onContactRelationshipChange(ContactRelationshipChanged event) async* {
    final contactRelationship = ContactRelationshipInput.dirty(event.contactRelationship);
    yield this.state.copyWith(
      contactRelationship: contactRelationship,
      formStatus: Formz.validate([
      this.state.name,
        contactRelationship,
      this.state.isKeyHolder,
      this.state.infoSharingConsentGiven,
      this.state.preferredContactNumber,
      this.state.fullAddress,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmergencyContactState> onIsKeyHolderChange(IsKeyHolderChanged event) async* {
    final isKeyHolder = IsKeyHolderInput.dirty(event.isKeyHolder);
    yield this.state.copyWith(
      isKeyHolder: isKeyHolder,
      formStatus: Formz.validate([
      this.state.name,
      this.state.contactRelationship,
        isKeyHolder,
      this.state.infoSharingConsentGiven,
      this.state.preferredContactNumber,
      this.state.fullAddress,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmergencyContactState> onInfoSharingConsentGivenChange(InfoSharingConsentGivenChanged event) async* {
    final infoSharingConsentGiven = InfoSharingConsentGivenInput.dirty(event.infoSharingConsentGiven);
    yield this.state.copyWith(
      infoSharingConsentGiven: infoSharingConsentGiven,
      formStatus: Formz.validate([
      this.state.name,
      this.state.contactRelationship,
      this.state.isKeyHolder,
        infoSharingConsentGiven,
      this.state.preferredContactNumber,
      this.state.fullAddress,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmergencyContactState> onPreferredContactNumberChange(PreferredContactNumberChanged event) async* {
    final preferredContactNumber = PreferredContactNumberInput.dirty(event.preferredContactNumber);
    yield this.state.copyWith(
      preferredContactNumber: preferredContactNumber,
      formStatus: Formz.validate([
      this.state.name,
      this.state.contactRelationship,
      this.state.isKeyHolder,
      this.state.infoSharingConsentGiven,
        preferredContactNumber,
      this.state.fullAddress,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmergencyContactState> onFullAddressChange(FullAddressChanged event) async* {
    final fullAddress = FullAddressInput.dirty(event.fullAddress);
    yield this.state.copyWith(
      fullAddress: fullAddress,
      formStatus: Formz.validate([
      this.state.name,
      this.state.contactRelationship,
      this.state.isKeyHolder,
      this.state.infoSharingConsentGiven,
      this.state.preferredContactNumber,
        fullAddress,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmergencyContactState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.name,
      this.state.contactRelationship,
      this.state.isKeyHolder,
      this.state.infoSharingConsentGiven,
      this.state.preferredContactNumber,
      this.state.fullAddress,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmergencyContactState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.name,
      this.state.contactRelationship,
      this.state.isKeyHolder,
      this.state.infoSharingConsentGiven,
      this.state.preferredContactNumber,
      this.state.fullAddress,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmergencyContactState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.name,
      this.state.contactRelationship,
      this.state.isKeyHolder,
      this.state.infoSharingConsentGiven,
      this.state.preferredContactNumber,
      this.state.fullAddress,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmergencyContactState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.name,
      this.state.contactRelationship,
      this.state.isKeyHolder,
      this.state.infoSharingConsentGiven,
      this.state.preferredContactNumber,
      this.state.fullAddress,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    nameController.dispose();
    contactRelationshipController.dispose();
    preferredContactNumberController.dispose();
    fullAddressController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}