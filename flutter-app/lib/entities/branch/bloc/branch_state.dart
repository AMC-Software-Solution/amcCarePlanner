part of 'branch_bloc.dart';

enum BranchStatusUI {init, loading, error, done}
enum BranchDeleteStatus {ok, ko, none}

class BranchState extends Equatable {
  final List<Branch> branches;
  final Branch loadedBranch;
  final bool editMode;
  final BranchDeleteStatus deleteStatus;
  final BranchStatusUI branchStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final NameInput name;
  final AddressInput address;
  final TelephoneInput telephone;
  final ContactNameInput contactName;
  final BranchEmailInput branchEmail;
  final PhotoUrlInput photoUrl;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final HasExtraDataInput hasExtraData;

  
  BranchState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.branches = const [],
    this.branchStatusUI = BranchStatusUI.init,
    this.loadedBranch = const Branch(0,'','','','','','',null,null,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = BranchDeleteStatus.none,
    this.name = const NameInput.pure(),
    this.address = const AddressInput.pure(),
    this.telephone = const TelephoneInput.pure(),
    this.contactName = const ContactNameInput.pure(),
    this.branchEmail = const BranchEmailInput.pure(),
    this.photoUrl = const PhotoUrlInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  BranchState copyWith({
    List<Branch> branches,
    BranchStatusUI branchStatusUI,
    bool editMode,
    BranchDeleteStatus deleteStatus,
    Branch loadedBranch,
    FormzStatus formStatus,
    String generalNotificationKey,
    NameInput name,
    AddressInput address,
    TelephoneInput telephone,
    ContactNameInput contactName,
    BranchEmailInput branchEmail,
    PhotoUrlInput photoUrl,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    HasExtraDataInput hasExtraData,
  }) {
    return BranchState(
        createdDate,
        lastUpdatedDate,
      branches: branches ?? this.branches,
      branchStatusUI: branchStatusUI ?? this.branchStatusUI,
      loadedBranch: loadedBranch ?? this.loadedBranch,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      name: name ?? this.name,
      address: address ?? this.address,
      telephone: telephone ?? this.telephone,
      contactName: contactName ?? this.contactName,
      branchEmail: branchEmail ?? this.branchEmail,
      photoUrl: photoUrl ?? this.photoUrl,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [branches, branchStatusUI,
     loadedBranch, editMode, deleteStatus, formStatus, generalNotificationKey, 
name,address,telephone,contactName,branchEmail,photoUrl,createdDate,lastUpdatedDate,hasExtraData,];

  @override
  bool get stringify => true;
}
