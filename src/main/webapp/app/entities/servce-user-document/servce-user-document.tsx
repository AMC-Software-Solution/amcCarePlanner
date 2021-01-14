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
import { getEntities } from './servce-user-document.reducer';
import { IServceUserDocument } from 'app/shared/model/servce-user-document.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IServceUserDocumentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ServceUserDocument = (props: IServceUserDocumentProps) => {
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

  const { servceUserDocumentList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="servce-user-document-heading">
        <Translate contentKey="carePlannerApp.servceUserDocument.home.title">Servce User Documents</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.servceUserDocument.home.createLabel">Create new Servce User Document</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {servceUserDocumentList && servceUserDocumentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentName')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.documentName">Document Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentNumber')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.documentNumber">Document Number</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentStatus')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.documentStatus">Document Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('issuedDate')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.issuedDate">Issued Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expiryDate')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.expiryDate">Expiry Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('uploadedDate')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.uploadedDate">Uploaded Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentFile')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.documentFile">Document File</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documentFileUrl')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.documentFileUrl">Document File Url</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hasExtraData')}>
                  <Translate contentKey="carePlannerApp.servceUserDocument.hasExtraData">Has Extra Data</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.servceUserDocument.owner">Owner</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.servceUserDocument.approvedBy">Approved By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {servceUserDocumentList.map((servceUserDocument, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${servceUserDocument.id}`} color="link" size="sm">
                      {servceUserDocument.id}
                    </Button>
                  </td>
                  <td>{servceUserDocument.documentName}</td>
                  <td>{servceUserDocument.documentNumber}</td>
                  <td>
                    <Translate contentKey={`carePlannerApp.DocumentStatus.${servceUserDocument.documentStatus}`} />
                  </td>
                  <td>{servceUserDocument.note}</td>
                  <td>
                    {servceUserDocument.issuedDate ? (
                      <TextFormat type="date" value={servceUserDocument.issuedDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {servceUserDocument.expiryDate ? (
                      <TextFormat type="date" value={servceUserDocument.expiryDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {servceUserDocument.uploadedDate ? (
                      <TextFormat type="date" value={servceUserDocument.uploadedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {servceUserDocument.documentFile ? (
                      <div>
                        {servceUserDocument.documentFileContentType ? (
                          <a onClick={openFile(servceUserDocument.documentFileContentType, servceUserDocument.documentFile)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {servceUserDocument.documentFileContentType}, {byteSize(servceUserDocument.documentFile)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{servceUserDocument.documentFileUrl}</td>
                  <td>
                    {servceUserDocument.createdDate ? (
                      <TextFormat type="date" value={servceUserDocument.createdDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {servceUserDocument.lastUpdatedDate ? (
                      <TextFormat type="date" value={servceUserDocument.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{servceUserDocument.clientId}</td>
                  <td>{servceUserDocument.hasExtraData ? 'true' : 'false'}</td>
                  <td>
                    {servceUserDocument.ownerServiceUserCode ? (
                      <Link to={`service-user/${servceUserDocument.ownerId}`}>{servceUserDocument.ownerServiceUserCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {servceUserDocument.approvedByEmployeeCode ? (
                      <Link to={`employee/${servceUserDocument.approvedById}`}>{servceUserDocument.approvedByEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${servceUserDocument.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${servceUserDocument.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${servceUserDocument.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="carePlannerApp.servceUserDocument.home.notFound">No Servce User Documents found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={servceUserDocumentList && servceUserDocumentList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ servceUserDocument }: IRootState) => ({
  servceUserDocumentList: servceUserDocument.entities,
  loading: servceUserDocument.loading,
  totalItems: servceUserDocument.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServceUserDocument);
