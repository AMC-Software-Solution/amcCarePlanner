part of 'medical_contact_bloc.dart';

enum MedicalContactStatusUI {init, loading, error, done}
enum MedicalContactDeleteStatus {ok, ko, none}

class MedicalContactState extends Equatable {
  final List<MedicalContact> medicalContacts;
  final MedicalContact loadedMedicalContact;
  final bool editMode;
  final MedicalContactDeleteStatus deleteStatus;
  final MedicalContactStatusUI medicalContactStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final DoctorNameInput doctorName;
  final DoctorSurgeryInput doctorSurgery;
  final DoctorAddressInput doctorAddress;
  final DoctorPhoneInput doctorPhone;
  final LastVisitedDoctorInput lastVisitedDoctor;
  final DistrictNurseNameInput districtNurseName;
  final DistrictNursePhoneInput districtNursePhone;
  final CareManagerNameInput careManagerName;
  final CareManagerPhoneInput careManagerPhone;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  MedicalContactState(
LastVisitedDoctorInput lastVisitedDoctor,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.medicalContacts = const [],
    this.medicalContactStatusUI = MedicalContactStatusUI.init,
    this.loadedMedicalContact = const MedicalContact(0,'','','','',null,'','','','',null,null,0,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = MedicalContactDeleteStatus.none,
    this.doctorName = const DoctorNameInput.pure(),
    this.doctorSurgery = const DoctorSurgeryInput.pure(),
    this.doctorAddress = const DoctorAddressInput.pure(),
    this.doctorPhone = const DoctorPhoneInput.pure(),
    this.districtNurseName = const DistrictNurseNameInput.pure(),
    this.districtNursePhone = const DistrictNursePhoneInput.pure(),
    this.careManagerName = const CareManagerNameInput.pure(),
    this.careManagerPhone = const CareManagerPhoneInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.lastVisitedDoctor = lastVisitedDoctor ?? LastVisitedDoctorInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  MedicalContactState copyWith({
    List<MedicalContact> medicalContacts,
    MedicalContactStatusUI medicalContactStatusUI,
    bool editMode,
    MedicalContactDeleteStatus deleteStatus,
    MedicalContact loadedMedicalContact,
    FormzStatus formStatus,
    String generalNotificationKey,
    DoctorNameInput doctorName,
    DoctorSurgeryInput doctorSurgery,
    DoctorAddressInput doctorAddress,
    DoctorPhoneInput doctorPhone,
    LastVisitedDoctorInput lastVisitedDoctor,
    DistrictNurseNameInput districtNurseName,
    DistrictNursePhoneInput districtNursePhone,
    CareManagerNameInput careManagerName,
    CareManagerPhoneInput careManagerPhone,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return MedicalContactState(
        lastVisitedDoctor,
        createdDate,
        lastUpdatedDate,
      medicalContacts: medicalContacts ?? this.medicalContacts,
      medicalContactStatusUI: medicalContactStatusUI ?? this.medicalContactStatusUI,
      loadedMedicalContact: loadedMedicalContact ?? this.loadedMedicalContact,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      doctorName: doctorName ?? this.doctorName,
      doctorSurgery: doctorSurgery ?? this.doctorSurgery,
      doctorAddress: doctorAddress ?? this.doctorAddress,
      doctorPhone: doctorPhone ?? this.doctorPhone,
      districtNurseName: districtNurseName ?? this.districtNurseName,
      districtNursePhone: districtNursePhone ?? this.districtNursePhone,
      careManagerName: careManagerName ?? this.careManagerName,
      careManagerPhone: careManagerPhone ?? this.careManagerPhone,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [medicalContacts, medicalContactStatusUI,
     loadedMedicalContact, editMode, deleteStatus, formStatus, generalNotificationKey, 
doctorName,doctorSurgery,doctorAddress,doctorPhone,lastVisitedDoctor,districtNurseName,districtNursePhone,careManagerName,careManagerPhone,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
