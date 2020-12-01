import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './service-user-event.reducer';
import { IServiceUserEvent } from 'app/shared/model/service-user-event.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IServiceUserEventProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ServiceUserEvent = (props: IServiceUserEventProps) => {
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

  const { serviceUserEventList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="service-user-event-heading">
        <Translate contentKey="carePlannerApp.serviceUserEvent.home.title">Service User Events</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.serviceUserEvent.home.createLabel">Create new Service User Event</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {serviceUserEventList && serviceUserEventList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eventTitle')}>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.eventTitle">Event Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('serviceUserEventStatus')}>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.serviceUserEventStatus">Service User Event Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('serviceUserEventType')}>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.serviceUserEventType">Service User Event Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('priority')}>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.priority">Priority</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateOfEvent')}>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.dateOfEvent">Date Of Event</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.reportedBy">Reported By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.assignedTo">Assigned To</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.serviceUserEvent.serviceUser">Service User</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {serviceUserEventList.map((serviceUserEvent, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${serviceUserEvent.id}`} color="link" size="sm">
                      {serviceUserEvent.id}
                    </Button>
                  </td>
                  <td>{serviceUserEvent.eventTitle}</td>
                  <td>{serviceUserEvent.description}</td>
                  <td>
                    <Translate contentKey={`carePlannerApp.ServiceUserEventStatus.${serviceUserEvent.serviceUserEventStatus}`} />
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.ServiceUserEventType.${serviceUserEvent.serviceUserEventType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`carePlannerApp.ServicePriority.${serviceUserEvent.priority}`} />
                  </td>
                  <td>{serviceUserEvent.note}</td>
                  <td>
                    {serviceUserEvent.dateOfEvent ? (
                      <TextFormat type="date" value={serviceUserEvent.dateOfEvent} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {serviceUserEvent.lastUpdatedDate ? (
                      <TextFormat type="date" value={serviceUserEvent.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{serviceUserEvent.clientId}</td>
                  <td>
                    {serviceUserEvent.reportedByEmployeeCode ? (
                      <Link to={`employee/${serviceUserEvent.reportedById}`}>{serviceUserEvent.reportedByEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {serviceUserEvent.assignedToEmployeeCode ? (
                      <Link to={`employee/${serviceUserEvent.assignedToId}`}>{serviceUserEvent.assignedToEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {serviceUserEvent.serviceUserServiceUserCode ? (
                      <Link to={`service-user/${serviceUserEvent.serviceUserId}`}>{serviceUserEvent.serviceUserServiceUserCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${serviceUserEvent.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${serviceUserEvent.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${serviceUserEvent.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="carePlannerApp.serviceUserEvent.home.notFound">No Service User Events found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={serviceUserEventList && serviceUserEventList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ serviceUserEvent }: IRootState) => ({
  serviceUserEventList: serviceUserEvent.entities,
  loading: serviceUserEvent.loading,
  totalItems: serviceUserEvent.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUserEvent);
