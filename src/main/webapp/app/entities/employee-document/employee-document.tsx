import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import {
  openFile,
  byteSize,
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employee-document.reducer';
import { IEmployeeDocument } from 'app/shared/model/employee-document.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IEmployeeDocumentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EmployeeDocument = (props: IEmployeeDocumentProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { employeeDocumentList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="employee-document-heading">
        <Translate contentKey="carePlannerApp.employeeDocument.home.title">Employee Documents</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.employeeDocument.home.createLabel">Create new Employee Document</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {employeeDocumentList && employeeDocumentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentName')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.documentName">Document Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentNumber')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.documentNumber">Document Number</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentStatus')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.documentStatus">Document Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('issuedDate')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.issuedDate">Issued Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expiryDate')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.expiryDate">Expiry Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('uploadedDate')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.uploadedDate">Uploaded Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentFile')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.documentFile">Document File</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentFileUrl')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.documentFileUrl">Document File Url</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hasExtraData')}>
                  <Translate contentKey="carePlannerApp.employeeDocument.hasExtraData">Has Extra Data</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.employeeDocument.documentType">Document Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.employeeDocument.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.employeeDocument.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeDocumentList.map((employeeDocument, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${employeeDocument.id}`} color="link" size="sm">
                      {employeeDocument.id}
                    </Button>
                  </td>
                  <td>{employeeDocument.documentName}</td>
                  <td>{employeeDocument.documentNumber}</td>
                  <td>
                    <Translate contentKey={`carePlannerApp.DocumentStatus.${employeeDocument.documentStatus}`} />
                  </td>
                  <td>{employeeDocument.note}</td>
                  <td>
                    {employeeDocument.issuedDate ? (
                      <TextFormat type="date" value={employeeDocument.issuedDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeDocument.expiryDate ? (
                      <TextFormat type="date" value={employeeDocument.expiryDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeDocument.uploadedDate ? (
                      <TextFormat type="date" value={employeeDocument.uploadedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeDocument.documentFile ? (
                      <div>
                        {employeeDocument.documentFileContentType ? (
                          <a onClick={openFile(employeeDocument.documentFileContentType, employeeDocument.documentFile)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeDocument.documentFileContentType}, {byteSize(employeeDocument.documentFile)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{employeeDocument.documentFileUrl}</td>
                  <td>
                    {employeeDocument.createdDate ? (
                      <TextFormat type="date" value={employeeDocument.createdDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeDocument.lastUpdatedDate ? (
                      <TextFormat type="date" value={employeeDocument.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employeeDocument.clientId}</td>
                  <td>{employeeDocument.hasExtraData ? 'true' : 'false'}</td>
                  <td>
                    {employeeDocument.documentTypeDocumentTypeTitle ? (
                      <Link to={`document-type/${employeeDocument.documentTypeId}`}>{employeeDocument.documentTypeDocumentTypeTitle}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeDocument.employeeEmployeeCode ? (
                      <Link to={`employee/${employeeDocument.employeeId}`}>{employeeDocument.employeeEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeDocument.approvedByEmployeeCode ? (
                      <Link to={`employee/${employeeDocument.approvedById}`}>{employeeDocument.approvedByEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${employeeDocument.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${employeeDocument.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${employeeDocument.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="carePlannerApp.employeeDocument.home.notFound">No Employee Documents found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={employeeDocumentList && employeeDocumentList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ employeeDocument }: IRootState) => ({
  employeeDocumentList: employeeDocument.entities,
  loading: employeeDocument.loading,
  totalItems: employeeDocument.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDocument);
