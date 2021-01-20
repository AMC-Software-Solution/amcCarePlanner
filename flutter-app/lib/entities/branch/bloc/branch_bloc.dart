import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/branch/branch_model.dart';
import 'package:amcCarePlanner/entities/branch/branch_repository.dart';
import 'package:amcCarePlanner/entities/branch/bloc/branch_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'branch_events.dart';
part 'branch_state.dart';

class BranchBloc extends Bloc<BranchEvent, BranchState> {
  final BranchRepository _branchRepository;

  final nameController = TextEditingController();
  final addressController = TextEditingController();
  final telephoneController = TextEditingController();
  final contactNameController = TextEditingController();
  final branchEmailController = TextEditingController();
  final photoUrlController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();

  BranchBloc({@required BranchRepository branchRepository}) : assert(branchRepository != null),
        _branchRepository = branchRepository, 
  super(BranchState(null,null,));

  @override
  void onTransition(Transition<BranchEvent, BranchState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<BranchState> mapEventToState(BranchEvent event) async* {
    if (event is InitBranchList) {
      yield* onInitList(event);
    } else if (event is BranchFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadBranchByIdForEdit) {
      yield* onLoadBranchIdForEdit(event);
    } else if (event is DeleteBranchById) {
      yield* onDeleteBranchId(event);
    } else if (event is LoadBranchByIdForView) {
      yield* onLoadBranchIdForView(event);
    }else if (event is NameChanged){
      yield* onNameChange(event);
    }else if (event is AddressChanged){
      yield* onAddressChange(event);
    }else if (event is TelephoneChanged){
      yield* onTelephoneChange(event);
    }else if (event is ContactNameChanged){
      yield* onContactNameChange(event);
    }else if (event is BranchEmailChanged){
      yield* onBranchEmailChange(event);
    }else if (event is PhotoUrlChanged){
      yield* onPhotoUrlChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<BranchState> onInitList(InitBranchList event) async* {
    yield this.state.copyWith(branchStatusUI: BranchStatusUI.loading);
    List<Branch> branches = await _branchRepository.getAllBranches();
    yield this.state.copyWith(branches: branches, branchStatusUI: BranchStatusUI.done);
  }

  Stream<BranchState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Branch result;
        if(this.state.editMode) {
          Branch newBranch = Branch(state.loadedBranch.id,
            this.state.name.value,  
            this.state.address.value,  
            this.state.telephone.value,  
            this.state.contactName.value,  
            this.state.branchEmail.value,  
            this.state.photoUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _branchRepository.update(newBranch);
        } else {
          Branch newBranch = Branch(null,
            this.state.name.value,  
            this.state.address.value,  
            this.state.telephone.value,  
            this.state.contactName.value,  
            this.state.branchEmail.value,  
            this.state.photoUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _branchRepository.create(newBranch);
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

  Stream<BranchState> onLoadBranchIdForEdit(LoadBranchByIdForEdit event) async* {
    yield this.state.copyWith(branchStatusUI: BranchStatusUI.loading);
    Branch loadedBranch = await _branchRepository.getBranch(event.id);

    final name = NameInput.dirty(loadedBranch?.name != null ? loadedBranch.name: '');
    final address = AddressInput.dirty(loadedBranch?.address != null ? loadedBranch.address: '');
    final telephone = TelephoneInput.dirty(loadedBranch?.telephone != null ? loadedBranch.telephone: '');
    final contactName = ContactNameInput.dirty(loadedBranch?.contactName != null ? loadedBranch.contactName: '');
    final branchEmail = BranchEmailInput.dirty(loadedBranch?.branchEmail != null ? loadedBranch.branchEmail: '');
    final photoUrl = PhotoUrlInput.dirty(loadedBranch?.photoUrl != null ? loadedBranch.photoUrl: '');
    final createdDate = CreatedDateInput.dirty(loadedBranch?.createdDate != null ? loadedBranch.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedBranch?.lastUpdatedDate != null ? loadedBranch.lastUpdatedDate: null);
    final hasExtraData = HasExtraDataInput.dirty(loadedBranch?.hasExtraData != null ? loadedBranch.hasExtraData: false);

    yield this.state.copyWith(loadedBranch: loadedBranch, editMode: true,
      name: name,
      address: address,
      telephone: telephone,
      contactName: contactName,
      branchEmail: branchEmail,
      photoUrl: photoUrl,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      hasExtraData: hasExtraData,
    branchStatusUI: BranchStatusUI.done);

    nameController.text = loadedBranch.name;
    addressController.text = loadedBranch.address;
    telephoneController.text = loadedBranch.telephone;
    contactNameController.text = loadedBranch.contactName;
    branchEmailController.text = loadedBranch.branchEmail;
    photoUrlController.text = loadedBranch.photoUrl;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedBranch?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedBranch?.lastUpdatedDate);
  }

  Stream<BranchState> onDeleteBranchId(DeleteBranchById event) async* {
    try {
      await _branchRepository.delete(event.id);
      this.add(InitBranchList());
      yield this.state.copyWith(deleteStatus: BranchDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: BranchDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: BranchDeleteStatus.none);
  }

  Stream<BranchState> onLoadBranchIdForView(LoadBranchByIdForView event) async* {
    yield this.state.copyWith(branchStatusUI: BranchStatusUI.loading);
    try {
      Branch loadedBranch = await _branchRepository.getBranch(event.id);
      yield this.state.copyWith(loadedBranch: loadedBranch, branchStatusUI: BranchStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedBranch: null, branchStatusUI: BranchStatusUI.error);
    }
  }


  Stream<BranchState> onNameChange(NameChanged event) async* {
    final name = NameInput.dirty(event.name);
    yield this.state.copyWith(
      name: name,
      formStatus: Formz.validate([
        name,
      this.state.address,
      this.state.telephone,
      this.state.contactName,
      this.state.branchEmail,
      this.state.photoUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<BranchState> onAddressChange(AddressChanged event) async* {
    final address = AddressInput.dirty(event.address);
    yield this.state.copyWith(
      address: address,
      formStatus: Formz.validate([
      this.state.name,
        address,
      this.state.telephone,
      this.state.contactName,
      this.state.branchEmail,
      this.state.photoUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<BranchState> onTelephoneChange(TelephoneChanged event) async* {
    final telephone = TelephoneInput.dirty(event.telephone);
    yield this.state.copyWith(
      telephone: telephone,
      formStatus: Formz.validate([
      this.state.name,
      this.state.address,
        telephone,
      this.state.contactName,
      this.state.branchEmail,
      this.state.photoUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<BranchState> onContactNameChange(ContactNameChanged event) async* {
    final contactName = ContactNameInput.dirty(event.contactName);
    yield this.state.copyWith(
      contactName: contactName,
      formStatus: Formz.validate([
      this.state.name,
      this.state.address,
      this.state.telephone,
        contactName,
      this.state.branchEmail,
      this.state.photoUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<BranchState> onBranchEmailChange(BranchEmailChanged event) async* {
    final branchEmail = BranchEmailInput.dirty(event.branchEmail);
    yield this.state.copyWith(
      branchEmail: branchEmail,
      formStatus: Formz.validate([
      this.state.name,
      this.state.address,
      this.state.telephone,
      this.state.contactName,
        branchEmail,
      this.state.photoUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<BranchState> onPhotoUrlChange(PhotoUrlChanged event) async* {
    final photoUrl = PhotoUrlInput.dirty(event.photoUrl);
    yield this.state.copyWith(
      photoUrl: photoUrl,
      formStatus: Formz.validate([
      this.state.name,
      this.state.address,
      this.state.telephone,
      this.state.contactName,
      this.state.branchEmail,
        photoUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<BranchState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.name,
      this.state.address,
      this.state.telephone,
      this.state.contactName,
      this.state.branchEmail,
      this.state.photoUrl,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<BranchState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.name,
      this.state.address,
      this.state.telephone,
      this.state.contactName,
      this.state.branchEmail,
      this.state.photoUrl,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<BranchState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.name,
      this.state.address,
      this.state.telephone,
      this.state.contactName,
      this.state.branchEmail,
      this.state.photoUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    nameController.dispose();
    addressController.dispose();
    telephoneController.dispose();
    contactNameController.dispose();
    branchEmailController.dispose();
    photoUrlController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    return super.close();
  }

}