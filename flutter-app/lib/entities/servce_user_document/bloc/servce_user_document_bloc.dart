import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/servce_user_document/servce_user_document_model.dart';
import 'package:amcCarePlanner/entities/servce_user_document/servce_user_document_repository.dart';
import 'package:amcCarePlanner/entities/servce_user_document/bloc/servce_user_document_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'servce_user_document_events.dart';
part 'servce_user_document_state.dart';

class ServceUserDocumentBloc extends Bloc<ServceUserDocumentEvent, ServceUserDocumentState> {
  final ServceUserDocumentRepository _servceUserDocumentRepository;

  final documentNameController = TextEditingController();
  final documentNumberController = TextEditingController();
  final noteController = TextEditingController();
  final issuedDateController = TextEditingController();
  final expiryDateController = TextEditingController();
  final uploadedDateController = TextEditingController();
  final documentFileUrlController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  ServceUserDocumentBloc({@required ServceUserDocumentRepository servceUserDocumentRepository}) : assert(servceUserDocumentRepository != null),
        _servceUserDocumentRepository = servceUserDocumentRepository, 
  super(ServceUserDocumentState(null,null,null,null,null,));

  @override
  void onTransition(Transition<ServceUserDocumentEvent, ServceUserDocumentState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ServceUserDocumentState> mapEventToState(ServceUserDocumentEvent event) async* {
    if (event is InitServceUserDocumentList) {
      yield* onInitList(event);
    } else if (event is ServceUserDocumentFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadServceUserDocumentByIdForEdit) {
      yield* onLoadServceUserDocumentIdForEdit(event);
    } else if (event is DeleteServceUserDocumentById) {
      yield* onDeleteServceUserDocumentId(event);
    } else if (event is LoadServceUserDocumentByIdForView) {
      yield* onLoadServceUserDocumentIdForView(event);
    }else if (event is DocumentNameChanged){
      yield* onDocumentNameChange(event);
    }else if (event is DocumentNumberChanged){
      yield* onDocumentNumberChange(event);
    }else if (event is DocumentStatusChanged){
      yield* onDocumentStatusChange(event);
    }else if (event is NoteChanged){
      yield* onNoteChange(event);
    }else if (event is IssuedDateChanged){
      yield* onIssuedDateChange(event);
    }else if (event is ExpiryDateChanged){
      yield* onExpiryDateChange(event);
    }else if (event is UploadedDateChanged){
      yield* onUploadedDateChange(event);
    }else if (event is DocumentFileUrlChanged){
      yield* onDocumentFileUrlChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ServceUserDocumentState> onInitList(InitServceUserDocumentList event) async* {
    yield this.state.copyWith(servceUserDocumentStatusUI: ServceUserDocumentStatusUI.loading);
    List<ServceUserDocument> servceUserDocuments = await _servceUserDocumentRepository.getAllServceUserDocuments();
    yield this.state.copyWith(servceUserDocuments: servceUserDocuments, servceUserDocumentStatusUI: ServceUserDocumentStatusUI.done);
  }

  Stream<ServceUserDocumentState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        ServceUserDocument result;
        if(this.state.editMode) {
          ServceUserDocument newServceUserDocument = ServceUserDocument(state.loadedServceUserDocument.id,
            this.state.documentName.value,  
            this.state.documentNumber.value,  
            this.state.documentStatus.value,  
            this.state.note.value,  
            this.state.issuedDate.value,  
            this.state.expiryDate.value,  
            this.state.uploadedDate.value,  
            this.state.documentFileUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _servceUserDocumentRepository.update(newServceUserDocument);
        } else {
          ServceUserDocument newServceUserDocument = ServceUserDocument(null,
            this.state.documentName.value,  
            this.state.documentNumber.value,  
            this.state.documentStatus.value,  
            this.state.note.value,  
            this.state.issuedDate.value,  
            this.state.expiryDate.value,  
            this.state.uploadedDate.value,  
            this.state.documentFileUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _servceUserDocumentRepository.create(newServceUserDocument);
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

  Stream<ServceUserDocumentState> onLoadServceUserDocumentIdForEdit(LoadServceUserDocumentByIdForEdit event) async* {
    yield this.state.copyWith(servceUserDocumentStatusUI: ServceUserDocumentStatusUI.loading);
    ServceUserDocument loadedServceUserDocument = await _servceUserDocumentRepository.getServceUserDocument(event.id);

    final documentName = DocumentNameInput.dirty(loadedServceUserDocument?.documentName != null ? loadedServceUserDocument.documentName: '');
    final documentNumber = DocumentNumberInput.dirty(loadedServceUserDocument?.documentNumber != null ? loadedServceUserDocument.documentNumber: '');
    final documentStatus = DocumentStatusInput.dirty(loadedServceUserDocument?.documentStatus != null ? loadedServceUserDocument.documentStatus: null);
    final note = NoteInput.dirty(loadedServceUserDocument?.note != null ? loadedServceUserDocument.note: '');
    final issuedDate = IssuedDateInput.dirty(loadedServceUserDocument?.issuedDate != null ? loadedServceUserDocument.issuedDate: null);
    final expiryDate = ExpiryDateInput.dirty(loadedServceUserDocument?.expiryDate != null ? loadedServceUserDocument.expiryDate: null);
    final uploadedDate = UploadedDateInput.dirty(loadedServceUserDocument?.uploadedDate != null ? loadedServceUserDocument.uploadedDate: null);
    final documentFileUrl = DocumentFileUrlInput.dirty(loadedServceUserDocument?.documentFileUrl != null ? loadedServceUserDocument.documentFileUrl: '');
    final createdDate = CreatedDateInput.dirty(loadedServceUserDocument?.createdDate != null ? loadedServceUserDocument.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedServceUserDocument?.lastUpdatedDate != null ? loadedServceUserDocument.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedServceUserDocument?.clientId != null ? loadedServceUserDocument.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedServceUserDocument?.hasExtraData != null ? loadedServceUserDocument.hasExtraData: false);

    yield this.state.copyWith(loadedServceUserDocument: loadedServceUserDocument, editMode: true,
      documentName: documentName,
      documentNumber: documentNumber,
      documentStatus: documentStatus,
      note: note,
      issuedDate: issuedDate,
      expiryDate: expiryDate,
      uploadedDate: uploadedDate,
      documentFileUrl: documentFileUrl,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    servceUserDocumentStatusUI: ServceUserDocumentStatusUI.done);

    documentNameController.text = loadedServceUserDocument.documentName;
    documentNumberController.text = loadedServceUserDocument.documentNumber;
    noteController.text = loadedServceUserDocument.note;
    issuedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServceUserDocument?.issuedDate);
    expiryDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServceUserDocument?.expiryDate);
    uploadedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServceUserDocument?.uploadedDate);
    documentFileUrlController.text = loadedServceUserDocument.documentFileUrl;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServceUserDocument?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServceUserDocument?.lastUpdatedDate);
    clientIdController.text = loadedServceUserDocument.clientId.toString();
  }

  Stream<ServceUserDocumentState> onDeleteServceUserDocumentId(DeleteServceUserDocumentById event) async* {
    try {
      await _servceUserDocumentRepository.delete(event.id);
      this.add(InitServceUserDocumentList());
      yield this.state.copyWith(deleteStatus: ServceUserDocumentDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ServceUserDocumentDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ServceUserDocumentDeleteStatus.none);
  }

  Stream<ServceUserDocumentState> onLoadServceUserDocumentIdForView(LoadServceUserDocumentByIdForView event) async* {
    yield this.state.copyWith(servceUserDocumentStatusUI: ServceUserDocumentStatusUI.loading);
    try {
      ServceUserDocument loadedServceUserDocument = await _servceUserDocumentRepository.getServceUserDocument(event.id);
      yield this.state.copyWith(loadedServceUserDocument: loadedServceUserDocument, servceUserDocumentStatusUI: ServceUserDocumentStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedServceUserDocument: null, servceUserDocumentStatusUI: ServceUserDocumentStatusUI.error);
    }
  }


  Stream<ServceUserDocumentState> onDocumentNameChange(DocumentNameChanged event) async* {
    final documentName = DocumentNameInput.dirty(event.documentName);
    yield this.state.copyWith(
      documentName: documentName,
      formStatus: Formz.validate([
        documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onDocumentNumberChange(DocumentNumberChanged event) async* {
    final documentNumber = DocumentNumberInput.dirty(event.documentNumber);
    yield this.state.copyWith(
      documentNumber: documentNumber,
      formStatus: Formz.validate([
      this.state.documentName,
        documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onDocumentStatusChange(DocumentStatusChanged event) async* {
    final documentStatus = DocumentStatusInput.dirty(event.documentStatus);
    yield this.state.copyWith(
      documentStatus: documentStatus,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
        documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
        note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onIssuedDateChange(IssuedDateChanged event) async* {
    final issuedDate = IssuedDateInput.dirty(event.issuedDate);
    yield this.state.copyWith(
      issuedDate: issuedDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
        issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onExpiryDateChange(ExpiryDateChanged event) async* {
    final expiryDate = ExpiryDateInput.dirty(event.expiryDate);
    yield this.state.copyWith(
      expiryDate: expiryDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
        expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onUploadedDateChange(UploadedDateChanged event) async* {
    final uploadedDate = UploadedDateInput.dirty(event.uploadedDate);
    yield this.state.copyWith(
      uploadedDate: uploadedDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
        uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onDocumentFileUrlChange(DocumentFileUrlChanged event) async* {
    final documentFileUrl = DocumentFileUrlInput.dirty(event.documentFileUrl);
    yield this.state.copyWith(
      documentFileUrl: documentFileUrl,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
        documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServceUserDocumentState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.documentName,
      this.state.documentNumber,
      this.state.documentStatus,
      this.state.note,
      this.state.issuedDate,
      this.state.expiryDate,
      this.state.uploadedDate,
      this.state.documentFileUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    documentNameController.dispose();
    documentNumberController.dispose();
    noteController.dispose();
    issuedDateController.dispose();
    expiryDateController.dispose();
    uploadedDateController.dispose();
    documentFileUrlController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}