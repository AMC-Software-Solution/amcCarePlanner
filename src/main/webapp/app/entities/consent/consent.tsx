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
import { getEntities } from './consent.reducer';
import { IConsent } from 'app/shared/model/consent.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IConsentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Consent = (props: IConsentProps) => {
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

  const { consentList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="consent-heading">
        <Translate contentKey="carePlannerApp.consent.home.title">Consents</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.consent.home.createLabel">Create new Consent</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {consentList && consentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="carePlannerApp.consent.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="carePlannerApp.consent.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('giveConsent')}>
                  <Translate contentKey="carePlannerApp.consent.giveConsent">Give Consent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('arrangements')}>
                  <Translate contentKey="carePlannerApp.consent.arrangements">Arrangements</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('serviceUserSignature')}>
                  <Translate contentKey="carePlannerApp.consent.serviceUserSignature">Service User Signature</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('signatureImage')}>
                  <Translate contentKey="carePlannerApp.consent.signatureImage">Signature Image</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('signatureImageUrl')}>
                  <Translate contentKey="carePlannerApp.consent.signatureImageUrl">Signature Image Url</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('consentDate')}>
                  <Translate contentKey="carePlannerApp.consent.consentDate">Consent Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.consent.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantId')}>
                  <Translate contentKey="carePlannerApp.consent.tenantId">Tenant Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.consent.serviceUser">Service User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.consent.witnessedBy">Witnessed By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {consentList.map((consent, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${consent.id}`} color="link" size="sm">
                      {consent.id}
                    </Button>
                  </td>
                  <td>{consent.title}</td>
                  <td>{consent.description}</td>
                  <td>{consent.giveConsent ? 'true' : 'false'}</td>
                  <td>{consent.arrangements}</td>
                  <td>{consent.serviceUserSignature}</td>
                  <td>
                    {consent.signatureImage ? (
                      <div>
                        {consent.signatureImageContentType ? (
                          <a onClick={openFile(consent.signatureImageContentType, consent.signatureImage)}>
                            <img
                              src={`data:${consent.signatureImageContentType};base64,${consent.signatureImage}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {consent.signatureImageContentType}, {byteSize(consent.signatureImage)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{consent.signatureImageUrl}</td>
                  <td>{consent.consentDate ? <TextFormat type="date" value={consent.consentDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {consent.lastUpdatedDate ? <TextFormat type="date" value={consent.lastUpdatedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{consent.tenantId}</td>
                  <td>
                    {consent.serviceUserServiceUserCode ? (
                      <Link to={`service-user/${consent.serviceUserId}`}>{consent.serviceUserServiceUserCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {consent.witnessedByEmployeeCode ? (
                      <Link to={`employee/${consent.witnessedById}`}>{consent.witnessedByEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${consent.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${consent.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${consent.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="carePlannerApp.consent.home.notFound">No Consents found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={consentList && consentList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ consent }: IRootState) => ({
  consentList: consent.entities,
  loading: consent.loading,
  totalItems: consent.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Consent);
