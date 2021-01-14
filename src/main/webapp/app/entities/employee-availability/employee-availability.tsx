import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employee-availability.reducer';
import { IEmployeeAvailability } from 'app/shared/model/employee-availability.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IEmployeeAvailabilityProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EmployeeAvailability = (props: IEmployeeAvailabilityProps) => {
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

  const { employeeAvailabilityList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="employee-availability-heading">
        <Translate contentKey="carePlannerApp.employeeAvailability.home.title">Employee Availabilities</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.employeeAvailability.home.createLabel">Create new Employee Availability</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {employeeAvailabilityList && employeeAvailabilityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('availableForWork')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableForWork">Available For Work</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('availableMonday')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableMonday">Available Monday</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('availableTuesday')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableTuesday">Available Tuesday</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('availableWednesday')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableWednesday">Available Wednesday</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('availableThursday')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableThursday">Available Thursday</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('availableFriday')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableFriday">Available Friday</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('availableSaturday')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableSaturday">Available Saturday</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('availableSunday')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableSunday">Available Sunday</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('preferredShift')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.preferredShift">Preferred Shift</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hasExtraData')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.hasExtraData">Has Extra Data</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.employeeAvailability.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.employeeAvailability.employee">Employee</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeAvailabilityList.map((employeeAvailability, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${employeeAvailability.id}`} color="link" size="sm">
                      {employeeAvailability.id}
                    </Button>
                  </td>
                  <td>{employeeAvailability.availableForWork ? 'true' : 'false'}</td>
                  <td>{employeeAvailability.availableMonday ? 'true' : 'false'}</td>
                  <td>{employeeAvailability.availableTuesday ? 'true' : 'false'}</td>
                  <td>{employeeAvailability.availableWednesday ? 'true' : 'false'}</td>
                  <td>{employeeAvailability.availableThursday ? 'true' : 'false'}</td>
                  <td>{employeeAvailability.availableFriday ? 'true' : 'false'}</td>
                  <td>{employeeAvailability.availableSaturday ? 'true' : 'false'}</td>
                  <td>{employeeAvailability.availableSunday ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`carePlannerApp.Shift.${employeeAvailability.preferredShift}`} />
                  </td>
                  <td>{employeeAvailability.hasExtraData ? 'true' : 'false'}</td>
                  <td>
                    {employeeAvailability.lastUpdatedDate ? (
                      <TextFormat type="date" value={employeeAvailability.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employeeAvailability.clientId}</td>
                  <td>
                    {employeeAvailability.employeeEmployeeCode ? (
                      <Link to={`employee/${employeeAvailability.employeeId}`}>{employeeAvailability.employeeEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${employeeAvailability.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${employeeAvailability.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${employeeAvailability.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="carePlannerApp.employeeAvailability.home.notFound">No Employee Availabilities found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={employeeAvailabilityList && employeeAvailabilityList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ employeeAvailability }: IRootState) => ({
  employeeAvailabilityList: employeeAvailability.entities,
  loading: employeeAvailability.loading,
  totalItems: employeeAvailability.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeAvailability);
