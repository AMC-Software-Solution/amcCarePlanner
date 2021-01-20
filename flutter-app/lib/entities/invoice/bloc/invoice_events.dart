part of 'invoice_bloc.dart';

abstract class InvoiceEvent extends Equatable {
  const InvoiceEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitInvoiceList extends InvoiceEvent {}

class TotalAmountChanged extends InvoiceEvent {
  final String totalAmount;
  
  const TotalAmountChanged({@required this.totalAmount});
  
  @override
  List<Object> get props => [totalAmount];
}
class DescriptionChanged extends InvoiceEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class InvoiceNumberChanged extends InvoiceEvent {
  final String invoiceNumber;
  
  const InvoiceNumberChanged({@required this.invoiceNumber});
  
  @override
  List<Object> get props => [invoiceNumber];
}
class GeneratedDateChanged extends InvoiceEvent {
  final DateTime generatedDate;
  
  const GeneratedDateChanged({@required this.generatedDate});
  
  @override
  List<Object> get props => [generatedDate];
}
class DueDateChanged extends InvoiceEvent {
  final DateTime dueDate;
  
  const DueDateChanged({@required this.dueDate});
  
  @override
  List<Object> get props => [dueDate];
}
class PaymentDateChanged extends InvoiceEvent {
  final DateTime paymentDate;
  
  const PaymentDateChanged({@required this.paymentDate});
  
  @override
  List<Object> get props => [paymentDate];
}
class InvoiceStatusChanged extends InvoiceEvent {
  final InvoiceStatus invoiceStatus;
  
  const InvoiceStatusChanged({@required this.invoiceStatus});
  
  @override
  List<Object> get props => [invoiceStatus];
}
class TaxChanged extends InvoiceEvent {
  final String tax;
  
  const TaxChanged({@required this.tax});
  
  @override
  List<Object> get props => [tax];
}
class Attribute1Changed extends InvoiceEvent {
  final String attribute1;
  
  const Attribute1Changed({@required this.attribute1});
  
  @override
  List<Object> get props => [attribute1];
}
class Attribute2Changed extends InvoiceEvent {
  final String attribute2;
  
  const Attribute2Changed({@required this.attribute2});
  
  @override
  List<Object> get props => [attribute2];
}
class Attribute3Changed extends InvoiceEvent {
  final String attribute3;
  
  const Attribute3Changed({@required this.attribute3});
  
  @override
  List<Object> get props => [attribute3];
}
class Attribute4Changed extends InvoiceEvent {
  final String attribute4;
  
  const Attribute4Changed({@required this.attribute4});
  
  @override
  List<Object> get props => [attribute4];
}
class Attribute5Changed extends InvoiceEvent {
  final String attribute5;
  
  const Attribute5Changed({@required this.attribute5});
  
  @override
  List<Object> get props => [attribute5];
}
class Attribute6Changed extends InvoiceEvent {
  final String attribute6;
  
  const Attribute6Changed({@required this.attribute6});
  
  @override
  List<Object> get props => [attribute6];
}
class Attribute7Changed extends InvoiceEvent {
  final String attribute7;
  
  const Attribute7Changed({@required this.attribute7});
  
  @override
  List<Object> get props => [attribute7];
}
class CreatedDateChanged extends InvoiceEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends InvoiceEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends InvoiceEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends InvoiceEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class InvoiceFormSubmitted extends InvoiceEvent {}

class LoadInvoiceByIdForEdit extends InvoiceEvent {
  final int id;

  const LoadInvoiceByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteInvoiceById extends InvoiceEvent {
  final int id;

  const DeleteInvoiceById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadInvoiceByIdForView extends InvoiceEvent {
  final int id;

  const LoadInvoiceByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
