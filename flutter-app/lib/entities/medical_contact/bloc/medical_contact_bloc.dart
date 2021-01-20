import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/medical_contact/medical_contact_model.dart';
import 'package:amcCarePlanner/entities/medical_contact/medical_contact_repository.dart';
import 'package:amcCarePlanner/entities/medical_contact/bloc/medical_contact_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'medical_contact_events.dart';
part 'medical_contact_state.dart';

class MedicalContactBloc extends Bloc<MedicalContactEvent, MedicalContactState> {
  final MedicalContactRepository _medicalContactRepository;

  final doctorNameController = TextEditingController();
  final doctorSurgeryController = TextEditingController();
  final doctorAddressController = TextEditingController();
  final doctorPhoneController = TextEditingController();
  final lastVisitedDoctorController = TextEditingController();
  final districtNurseNameController = TextEditingController();
  final districtNursePhoneController = TextEditingController();
  final careManagerNameController = TextEditingController();
  final careManagerPhoneController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  MedicalContactBloc({@required MedicalContactRepository medicalContactRepository}) : assert(medicalContactRepository != null),
        _medicalContactRepository = medicalContactRepository, 
  super(MedicalContactState(null,null,null,));

  @override
  void onTransition(Transition<MedicalContactEvent, MedicalContactState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<MedicalContactState> mapEventToState(MedicalContactEvent event) async* {
    if (event is InitMedicalContactList) {
      yield* onInitList(event);
    } else if (event is MedicalContactFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadMedicalContactByIdForEdit) {
      yield* onLoadMedicalContactIdForEdit(event);
    } else if (event is DeleteMedicalContactById) {
      yield* onDeleteMedicalContactId(event);
    } else if (event is LoadMedicalContactByIdForView) {
      yield* onLoadMedicalContactIdForView(event);
    }else if (event is DoctorNameChanged){
      yield* onDoctorNameChange(event);
    }else if (event is DoctorSurgeryChanged){
      yield* onDoctorSurgeryChange(event);
    }else if (event is DoctorAddressChanged){
      yield* onDoctorAddressChange(event);
    }else if (event is DoctorPhoneChanged){
      yield* onDoctorPhoneChange(event);
    }else if (event is LastVisitedDoctorChanged){
      yield* onLastVisitedDoctorChange(event);
    }else if (event is DistrictNurseNameChanged){
      yield* onDistrictNurseNameChange(event);
    }else if (event is DistrictNursePhoneChanged){
      yield* onDistrictNursePhoneChange(event);
    }else if (event is CareManagerNameChanged){
      yield* onCareManagerNameChange(event);
    }else if (event is CareManagerPhoneChanged){
      yield* onCareManagerPhoneChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<MedicalContactState> onInitList(InitMedicalContactList event) async* {
    yield this.state.copyWith(medicalContactStatusUI: MedicalContactStatusUI.loading);
    List<MedicalContact> medicalContacts = await _medicalContactRepository.getAllMedicalContacts();
    yield this.state.copyWith(medicalContacts: medicalContacts, medicalContactStatusUI: MedicalContactStatusUI.done);
  }

  Stream<MedicalContactState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        MedicalContact result;
        if(this.state.editMode) {
          MedicalContact newMedicalContact = MedicalContact(state.loadedMedicalContact.id,
            this.state.doctorName.value,  
            this.state.doctorSurgery.value,  
            this.state.doctorAddress.value,  
            this.state.doctorPhone.value,  
            this.state.lastVisitedDoctor.value,  
            this.state.districtNurseName.value,  
            this.state.districtNursePhone.value,  
            this.state.careManagerName.value,  
            this.state.careManagerPhone.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _medicalContactRepository.update(newMedicalContact);
        } else {
          MedicalContact newMedicalContact = MedicalContact(null,
            this.state.doctorName.value,  
            this.state.doctorSurgery.value,  
            this.state.doctorAddress.value,  
            this.state.doctorPhone.value,  
            this.state.lastVisitedDoctor.value,  
            this.state.districtNurseName.value,  
            this.state.districtNursePhone.value,  
            this.state.careManagerName.value,  
            this.state.careManagerPhone.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _medicalContactRepository.create(newMedicalContact);
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

  Stream<MedicalContactState> onLoadMedicalContactIdForEdit(LoadMedicalContactByIdForEdit event) async* {
    yield this.state.copyWith(medicalContactStatusUI: MedicalContactStatusUI.loading);
    MedicalContact loadedMedicalContact = await _medicalContactRepository.getMedicalContact(event.id);

    final doctorName = DoctorNameInput.dirty(loadedMedicalContact?.doctorName != null ? loadedMedicalContact.doctorName: '');
    final doctorSurgery = DoctorSurgeryInput.dirty(loadedMedicalContact?.doctorSurgery != null ? loadedMedicalContact.doctorSurgery: '');
    final doctorAddress = DoctorAddressInput.dirty(loadedMedicalContact?.doctorAddress != null ? loadedMedicalContact.doctorAddress: '');
    final doctorPhone = DoctorPhoneInput.dirty(loadedMedicalContact?.doctorPhone != null ? loadedMedicalContact.doctorPhone: '');
    final lastVisitedDoctor = LastVisitedDoctorInput.dirty(loadedMedicalContact?.lastVisitedDoctor != null ? loadedMedicalContact.lastVisitedDoctor: null);
    final districtNurseName = DistrictNurseNameInput.dirty(loadedMedicalContact?.districtNurseName != null ? loadedMedicalContact.districtNurseName: '');
    final districtNursePhone = DistrictNursePhoneInput.dirty(loadedMedicalContact?.districtNursePhone != null ? loadedMedicalContact.districtNursePhone: '');
    final careManagerName = CareManagerNameInput.dirty(loadedMedicalContact?.careManagerName != null ? loadedMedicalContact.careManagerName: '');
    final careManagerPhone = CareManagerPhoneInput.dirty(loadedMedicalContact?.careManagerPhone != null ? loadedMedicalContact.careManagerPhone: '');
    final createdDate = CreatedDateInput.dirty(loadedMedicalContact?.createdDate != null ? loadedMedicalContact.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedMedicalContact?.lastUpdatedDate != null ? loadedMedicalContact.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedMedicalContact?.clientId != null ? loadedMedicalContact.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedMedicalContact?.hasExtraData != null ? loadedMedicalContact.hasExtraData: false);

    yield this.state.copyWith(loadedMedicalContact: loadedMedicalContact, editMode: true,
      doctorName: doctorName,
      doctorSurgery: doctorSurgery,
      doctorAddress: doctorAddress,
      doctorPhone: doctorPhone,
      lastVisitedDoctor: lastVisitedDoctor,
      districtNurseName: districtNurseName,
      districtNursePhone: districtNursePhone,
      careManagerName: careManagerName,
      careManagerPhone: careManagerPhone,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    medicalContactStatusUI: MedicalContactStatusUI.done);

    doctorNameController.text = loadedMedicalContact.doctorName;
    doctorSurgeryController.text = loadedMedicalContact.doctorSurgery;
    doctorAddressController.text = loadedMedicalContact.doctorAddress;
    doctorPhoneController.text = loadedMedicalContact.doctorPhone;
    lastVisitedDoctorController.text = DateFormat.yMMMMd(S.current.locale).format(loadedMedicalContact?.lastVisitedDoctor);
    districtNurseNameController.text = loadedMedicalContact.districtNurseName;
    districtNursePhoneController.text = loadedMedicalContact.districtNursePhone;
    careManagerNameController.text = loadedMedicalContact.careManagerName;
    careManagerPhoneController.text = loadedMedicalContact.careManagerPhone;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedMedicalContact?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedMedicalContact?.lastUpdatedDate);
    clientIdController.text = loadedMedicalContact.clientId.toString();
  }

  Stream<MedicalContactState> onDeleteMedicalContactId(DeleteMedicalContactById event) async* {
    try {
      await _medicalContactRepository.delete(event.id);
      this.add(InitMedicalContactList());
      yield this.state.copyWith(deleteStatus: MedicalContactDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: MedicalContactDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: MedicalContactDeleteStatus.none);
  }

  Stream<MedicalContactState> onLoadMedicalContactIdForView(LoadMedicalContactByIdForView event) async* {
    yield this.state.copyWith(medicalContactStatusUI: MedicalContactStatusUI.loading);
    try {
      MedicalContact loadedMedicalContact = await _medicalContactRepository.getMedicalContact(event.id);
      yield this.state.copyWith(loadedMedicalContact: loadedMedicalContact, medicalContactStatusUI: MedicalContactStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedMedicalContact: null, medicalContactStatusUI: MedicalContactStatusUI.error);
    }
  }


  Stream<MedicalContactState> onDoctorNameChange(DoctorNameChanged event) async* {
    final doctorName = DoctorNameInput.dirty(event.doctorName);
    yield this.state.copyWith(
      doctorName: doctorName,
      formStatus: Formz.validate([
        doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onDoctorSurgeryChange(DoctorSurgeryChanged event) async* {
    final doctorSurgery = DoctorSurgeryInput.dirty(event.doctorSurgery);
    yield this.state.copyWith(
      doctorSurgery: doctorSurgery,
      formStatus: Formz.validate([
      this.state.doctorName,
        doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onDoctorAddressChange(DoctorAddressChanged event) async* {
    final doctorAddress = DoctorAddressInput.dirty(event.doctorAddress);
    yield this.state.copyWith(
      doctorAddress: doctorAddress,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
        doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onDoctorPhoneChange(DoctorPhoneChanged event) async* {
    final doctorPhone = DoctorPhoneInput.dirty(event.doctorPhone);
    yield this.state.copyWith(
      doctorPhone: doctorPhone,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
        doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onLastVisitedDoctorChange(LastVisitedDoctorChanged event) async* {
    final lastVisitedDoctor = LastVisitedDoctorInput.dirty(event.lastVisitedDoctor);
    yield this.state.copyWith(
      lastVisitedDoctor: lastVisitedDoctor,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
        lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onDistrictNurseNameChange(DistrictNurseNameChanged event) async* {
    final districtNurseName = DistrictNurseNameInput.dirty(event.districtNurseName);
    yield this.state.copyWith(
      districtNurseName: districtNurseName,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
        districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onDistrictNursePhoneChange(DistrictNursePhoneChanged event) async* {
    final districtNursePhone = DistrictNursePhoneInput.dirty(event.districtNursePhone);
    yield this.state.copyWith(
      districtNursePhone: districtNursePhone,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
        districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onCareManagerNameChange(CareManagerNameChanged event) async* {
    final careManagerName = CareManagerNameInput.dirty(event.careManagerName);
    yield this.state.copyWith(
      careManagerName: careManagerName,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
        careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onCareManagerPhoneChange(CareManagerPhoneChanged event) async* {
    final careManagerPhone = CareManagerPhoneInput.dirty(event.careManagerPhone);
    yield this.state.copyWith(
      careManagerPhone: careManagerPhone,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
        careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<MedicalContactState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.doctorName,
      this.state.doctorSurgery,
      this.state.doctorAddress,
      this.state.doctorPhone,
      this.state.lastVisitedDoctor,
      this.state.districtNurseName,
      this.state.districtNursePhone,
      this.state.careManagerName,
      this.state.careManagerPhone,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    doctorNameController.dispose();
    doctorSurgeryController.dispose();
    doctorAddressController.dispose();
    doctorPhoneController.dispose();
    lastVisitedDoctorController.dispose();
    districtNurseNameController.dispose();
    districtNursePhoneController.dispose();
    careManagerNameController.dispose();
    careManagerPhoneController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}