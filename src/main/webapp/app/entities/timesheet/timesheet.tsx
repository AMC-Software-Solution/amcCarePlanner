import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './timesheet.reducer';
import { ITimesheet } from 'app/shared/model/timesheet.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ITimesheetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Timesheet = (props: ITimesheetProps) => {
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

  const { timesheetList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="timesheet-heading">
        <Translate contentKey="carePlannerApp.timesheet.home.title">Timesheets</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.timesheet.home.createLabel">Create new Timesheet</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {timesheetList && timesheetList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="carePlannerApp.timesheet.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('timesheetDate')}>
                  <Translate contentKey="carePlannerApp.timesheet.timesheetDate">Timesheet Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startTime')}>
                  <Translate contentKey="carePlannerApp.timesheet.startTime">Start Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('endTime')}>
                  <Translate contentKey="carePlannerApp.timesheet.endTime">End Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hoursWorked')}>
                  <Translate contentKey="carePlannerApp.timesheet.hoursWorked">Hours Worked</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('breakStartTime')}>
                  <Translate contentKey="carePlannerApp.timesheet.breakStartTime">Break Start Time</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('breakEndTime')}>
                  <Translate contentKey="carePlannerApp.timesheet.breakEndTime">Break End Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="carePlannerApp.timesheet.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.timesheet.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantId')}>
                  <Translate contentKey="carePlannerApp.timesheet.tenantId">Tenant Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.timesheet.serviceOrder">Service Order</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.timesheet.serviceUser">Service User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.timesheet.careProvider">Care Provider</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {timesheetList.map((timesheet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${timesheet.id}`} color="link" size="sm">
                      {timesheet.id}
                    </Button>
                  </td>
                  <td>{timesheet.description}</td>
                  <td>
                    {timesheet.timesheetDate ? (
                      <TextFormat type="date" value={timesheet.timesheetDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{timesheet.startTime}</td>
                  <td>{timesheet.endTime}</td>
                  <td>{timesheet.hoursWorked}</td>
                  <td>{timesheet.breakStartTime}</td>
                  <td>{timesheet.breakEndTime}</td>
                  <td>{timesheet.note}</td>
                  <td>
                    {timesheet.lastUpdatedDate ? (
                      <TextFormat type="date" value={timesheet.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{timesheet.tenantId}</td>
                  <td>
                    {timesheet.serviceOrderTitle ? (
                      <Link to={`service-order/${timesheet.serviceOrderId}`}>{timesheet.serviceOrderTitle}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {timesheet.serviceUserServiceUserCode ? (
                      <Link to={`service-user/${timesheet.serviceUserId}`}>{timesheet.serviceUserServiceUserCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {timesheet.careProviderEmployeeCode ? (
                      <Link to={`employee/${timesheet.careProviderId}`}>{timesheet.careProviderEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${timesheet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${timesheet.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${timesheet.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="carePlannerApp.timesheet.home.notFound">No Timesheets found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={timesheetList && timesheetList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ timesheet }: IRootState) => ({
  timesheetList: timesheet.entities,
  loading: timesheet.loading,
  totalItems: timesheet.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Timesheet);
