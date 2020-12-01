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
import { getEntities } from './service-user.reducer';
import { IServiceUser } from 'app/shared/model/service-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IServiceUserProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ServiceUser = (props: IServiceUserProps) => {
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

  const { serviceUserList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="service-user-heading">
        <Translate contentKey="carePlannerApp.serviceUser.home.title">Service Users</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.serviceUser.home.createLabel">Create new Service User</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {serviceUserList && serviceUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="carePlannerApp.serviceUser.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="carePlannerApp.serviceUser.firstName">First Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('middleName')}>
                  <Translate contentKey="carePlannerApp.serviceUser.middleName">Middle Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  <Translate contentKey="carePlannerApp.serviceUser.lastName">Last Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('preferredName')}>
                  <Translate contentKey="carePlannerApp.serviceUser.preferredName">Preferred Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="carePlannerApp.serviceUser.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('serviceUserCode')}>
                  <Translate contentKey="carePlannerApp.serviceUser.serviceUserCode">Service User Code</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateOfBirth')}>
                  <Translate contentKey="carePlannerApp.serviceUser.dateOfBirth">Date Of Birth</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastVisitDate')}>
                  <Translate contentKey="carePlannerApp.serviceUser.lastVisitDate">Last Visit Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startDate')}>
                  <Translate contentKey="carePlannerApp.serviceUser.startDate">Start Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('supportType')}>
                  <Translate contentKey="carePlannerApp.serviceUser.supportType">Support Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('serviceUserCategory')}>
                  <Translate contentKey="carePlannerApp.serviceUser.serviceUserCategory">Service User Category</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vulnerability')}>
                  <Translate contentKey="carePlannerApp.serviceUser.vulnerability">Vulnerability</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('servicePriority')}>
                  <Translate contentKey="carePlannerApp.serviceUser.servicePriority">Service Priority</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('source')}>
                  <Translate contentKey="carePlannerApp.serviceUser.source">Source</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="carePlannerApp.serviceUser.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstLanguage')}>
                  <Translate contentKey="carePlannerApp.serviceUser.firstLanguage">First Language</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('interpreterRequired')}>
                  <Translate contentKey="carePlannerApp.serviceUser.interpreterRequired">Interpreter Required</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activatedDate')}>
                  <Translate contentKey="carePlannerApp.serviceUser.activatedDate">Activated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('profilePhoto')}>
                  <Translate contentKey="carePlannerApp.serviceUser.profilePhoto">Profile Photo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('profilePhotoUrl')}>
                  <Translate contentKey="carePlannerApp.serviceUser.profilePhotoUrl">Profile Photo Url</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastRecordedHeight')}>
                  <Translate contentKey="carePlannerApp.serviceUser.lastRecordedHeight">Last Recorded Height</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastRecordedWeight')}>
                  <Translate contentKey="carePlannerApp.serviceUser.lastRecordedWeight">Last Recorded Weight</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hasMedicalCondition')}>
                  <Translate contentKey="carePlannerApp.serviceUser.hasMedicalCondition">Has Medical Condition</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('medicalConditionSummary')}>
                  <Translate contentKey="carePlannerApp.serviceUser.medicalConditionSummary">Medical Condition Summary</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.serviceUser.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.serviceUser.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.serviceUser.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.serviceUser.branch">Branch</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.serviceUser.registeredBy">Registered By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.serviceUser.activatedBy">Activated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {serviceUserList.map((serviceUser, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${serviceUser.id}`} color="link" size="sm">
                      {serviceUser.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.Title.${serviceUser.title}`} />
                  </td>
                  <td>{serviceUser.firstName}</td>
                  <td>{serviceUser.middleName}</td>
                  <td>{serviceUser.lastName}</td>
                  <td>{serviceUser.preferredName}</td>
                  <td>{serviceUser.email}</td>
                  <td>{serviceUser.serviceUserCode}</td>
                  <td>
                    {serviceUser.dateOfBirth ? (
                      <TextFormat type="date" value={serviceUser.dateOfBirth} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {serviceUser.lastVisitDate ? (
                      <TextFormat type="date" value={serviceUser.lastVisitDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {serviceUser.startDate ? <TextFormat type="date" value={serviceUser.startDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.SupportType.${serviceUser.supportType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.ServiceUserCategory.${serviceUser.serviceUserCategory}`} />
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.Vulnerability.${serviceUser.vulnerability}`} />
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.ServicePriority.${serviceUser.servicePriority}`} />
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.Source.${serviceUser.source}`} />
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.ServiceUserStatus.${serviceUser.status}`} />
                  </td>
                  <td>{serviceUser.firstLanguage}</td>
                  <td>{serviceUser.interpreterRequired ? 'true' : 'false'}</td>
                  <td>
                    {serviceUser.activatedDate ? (
                      <TextFormat type="date" value={serviceUser.activatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {serviceUser.profilePhoto ? (
                      <div>
                        {serviceUser.profilePhotoContentType ? (
                          <a onClick={openFile(serviceUser.profilePhotoContentType, serviceUser.profilePhoto)}>
                            <img
                              src={`data:${serviceUser.profilePhotoContentType};base64,${serviceUser.profilePhoto}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {serviceUser.profilePhotoContentType}, {byteSize(serviceUser.profilePhoto)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{serviceUser.profilePhotoUrl}</td>
                  <td>{serviceUser.lastRecordedHeight}</td>
                  <td>{serviceUser.lastRecordedWeight}</td>
                  <td>{serviceUser.hasMedicalCondition ? 'true' : 'false'}</td>
                  <td>{serviceUser.medicalConditionSummary}</td>
                  <td>
                    {serviceUser.lastUpdatedDate ? (
                      <TextFormat type="date" value={serviceUser.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{serviceUser.clientId}</td>
                  <td>{serviceUser.userLogin ? serviceUser.userLogin : ''}</td>
                  <td>{serviceUser.branchName ? <Link to={`branch/${serviceUser.branchId}`}>{serviceUser.branchName}</Link> : ''}</td>
                  <td>
                    {serviceUser.registeredByEmployeeCode ? (
                      <Link to={`employee/${serviceUser.registeredById}`}>{serviceUser.registeredByEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {serviceUser.activatedByEmployeeCode ? (
                      <Link to={`employee/${serviceUser.activatedById}`}>{serviceUser.activatedByEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${serviceUser.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${serviceUser.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${serviceUser.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="carePlannerApp.serviceUser.home.notFound">No Service Users found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={serviceUserList && serviceUserList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ serviceUser }: IRootState) => ({
  serviceUserList: serviceUser.entities,
  loading: serviceUser.loading,
  totalItems: serviceUser.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUser);
