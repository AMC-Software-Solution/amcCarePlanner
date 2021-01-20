import 'package:amcCarePlanner/entities/invoice/invoice_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class InvoiceRepository {
    InvoiceRepository();
  
  static final String uriEndpoint = '/invoices';

  Future<List<Invoice>> getAllInvoices() async {
    final allInvoicesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Invoice>>(allInvoicesRequest.body);
  }

  Future<Invoice> getInvoice(int id) async {
    final invoiceRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Invoice>(invoiceRequest.body);
  }

  Future<Invoice> create(Invoice invoice) async {
    final invoiceRequest = await HttpUtils.postRequest('$uriEndpoint', invoice);
    return JsonMapper.deserialize<Invoice>(invoiceRequest.body);
  }

  Future<Invoice> update(Invoice invoice) async {
    final invoiceRequest = await HttpUtils.putRequest('$uriEndpoint', invoice);
    return JsonMapper.deserialize<Invoice>(invoiceRequest.body);
  }

  Future<void> delete(int id) async {
    final invoiceRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
