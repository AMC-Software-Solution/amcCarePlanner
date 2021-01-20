import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/document_type/document_type_model.dart';
import 'package:amcCarePlanner/entities/document_type/document_type_repository.dart';
import 'package:amcCarePlanner/entities/document_type/bloc/document_type_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'document_type_events.dart';
part 'document_type_state.dart';

class DocumentTypeBloc extends Bloc<DocumentTypeEvent, DocumentTypeState> {
  final DocumentTypeRepository _documentTypeRepository;

  final documentTypeTitleController = TextEditingController();
  final documentTypeDescriptionController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();

  DocumentTypeBloc({@required DocumentTypeRepository documentTypeRepository}) : assert(documentTypeRepository != null),
        _documentTypeRepository = documentTypeRepository, 
  super(DocumentTypeState(null,null,));

  @override
  void onTransition(Transition<DocumentTypeEvent, DocumentTypeState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<DocumentTypeState> mapEventToState(DocumentTypeEvent event) async* {
    if (event is InitDocumentTypeList) {
      yield* onInitList(event);
    } else if (event is DocumentTypeFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadDocumentTypeByIdForEdit) {
      yield* onLoadDocumentTypeIdForEdit(event);
    } else if (event is DeleteDocumentTypeById) {
      yield* onDeleteDocumentTypeId(event);
    } else if (event is LoadDocumentTypeByIdForView) {
      yield* onLoadDocumentTypeIdForView(event);
    }else if (event is DocumentTypeTitleChanged){
      yield* onDocumentTypeTitleChange(event);
    }else if (event is DocumentTypeDescriptionChanged){
      yield* onDocumentTypeDescriptionChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<DocumentTypeState> onInitList(InitDocumentTypeList event) async* {
    yield this.state.copyWith(documentTypeStatusUI: DocumentTypeStatusUI.loading);
    List<DocumentType> documentTypes = await _documentTypeRepository.getAllDocumentTypes();
    yield this.state.copyWith(documentTypes: documentTypes, documentTypeStatusUI: DocumentTypeStatusUI.done);
  }

  Stream<DocumentTypeState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        DocumentType result;
        if(this.state.editMode) {
          DocumentType newDocumentType = DocumentType(state.loadedDocumentType.id,
            this.state.documentTypeTitle.value,  
            this.state.documentTypeDescription.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
          );

          result = await _documentTypeRepository.update(newDocumentType);
        } else {
          DocumentType newDocumentType = DocumentType(null,
            this.state.documentTypeTitle.value,  
            this.state.documentTypeDescription.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.hasExtraData.value,  
          );

          result = await _documentTypeRepository.create(newDocumentType);
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

  Stream<DocumentTypeState> onLoadDocumentTypeIdForEdit(LoadDocumentTypeByIdForEdit event) async* {
    yield this.state.copyWith(documentTypeStatusUI: DocumentTypeStatusUI.loading);
    DocumentType loadedDocumentType = await _documentTypeRepository.getDocumentType(event.id);

    final documentTypeTitle = DocumentTypeTitleInput.dirty(loadedDocumentType?.documentTypeTitle != null ? loadedDocumentType.documentTypeTitle: '');
    final documentTypeDescription = DocumentTypeDescriptionInput.dirty(loadedDocumentType?.documentTypeDescription != null ? loadedDocumentType.documentTypeDescription: '');
    final createdDate = CreatedDateInput.dirty(loadedDocumentType?.createdDate != null ? loadedDocumentType.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedDocumentType?.lastUpdatedDate != null ? loadedDocumentType.lastUpdatedDate: null);
    final hasExtraData = HasExtraDataInput.dirty(loadedDocumentType?.hasExtraData != null ? loadedDocumentType.hasExtraData: false);

    yield this.state.copyWith(loadedDocumentType: loadedDocumentType, editMode: true,
      documentTypeTitle: documentTypeTitle,
      documentTypeDescription: documentTypeDescription,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      hasExtraData: hasExtraData,
    documentTypeStatusUI: DocumentTypeStatusUI.done);

    documentTypeTitleController.text = loadedDocumentType.documentTypeTitle;
    documentTypeDescriptionController.text = loadedDocumentType.documentTypeDescription;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedDocumentType?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedDocumentType?.lastUpdatedDate);
  }

  Stream<DocumentTypeState> onDeleteDocumentTypeId(DeleteDocumentTypeById event) async* {
    try {
      await _documentTypeRepository.delete(event.id);
      this.add(InitDocumentTypeList());
      yield this.state.copyWith(deleteStatus: DocumentTypeDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: DocumentTypeDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: DocumentTypeDeleteStatus.none);
  }

  Stream<DocumentTypeState> onLoadDocumentTypeIdForView(LoadDocumentTypeByIdForView event) async* {
    yield this.state.copyWith(documentTypeStatusUI: DocumentTypeStatusUI.loading);
    try {
      DocumentType loadedDocumentType = await _documentTypeRepository.getDocumentType(event.id);
      yield this.state.copyWith(loadedDocumentType: loadedDocumentType, documentTypeStatusUI: DocumentTypeStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedDocumentType: null, documentTypeStatusUI: DocumentTypeStatusUI.error);
    }
  }


  Stream<DocumentTypeState> onDocumentTypeTitleChange(DocumentTypeTitleChanged event) async* {
    final documentTypeTitle = DocumentTypeTitleInput.dirty(event.documentTypeTitle);
    yield this.state.copyWith(
      documentTypeTitle: documentTypeTitle,
      formStatus: Formz.validate([
        documentTypeTitle,
      this.state.documentTypeDescription,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DocumentTypeState> onDocumentTypeDescriptionChange(DocumentTypeDescriptionChanged event) async* {
    final documentTypeDescription = DocumentTypeDescriptionInput.dirty(event.documentTypeDescription);
    yield this.state.copyWith(
      documentTypeDescription: documentTypeDescription,
      formStatus: Formz.validate([
      this.state.documentTypeTitle,
        documentTypeDescription,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DocumentTypeState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.documentTypeTitle,
      this.state.documentTypeDescription,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DocumentTypeState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.documentTypeTitle,
      this.state.documentTypeDescription,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DocumentTypeState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.documentTypeTitle,
      this.state.documentTypeDescription,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    documentTypeTitleController.dispose();
    documentTypeDescriptionController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    return super.close();
  }

}