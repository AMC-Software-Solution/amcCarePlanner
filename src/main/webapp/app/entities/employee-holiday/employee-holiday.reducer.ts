import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployeeHoliday, defaultValue } from 'app/shared/model/employee-holiday.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEEHOLIDAY_LIST: 'employeeHoliday/FETCH_EMPLOYEEHOLIDAY_LIST',
  FETCH_EMPLOYEEHOLIDAY: 'employeeHoliday/FETCH_EMPLOYEEHOLIDAY',
  CREATE_EMPLOYEEHOLIDAY: 'employeeHoliday/CREATE_EMPLOYEEHOLIDAY',
  UPDATE_EMPLOYEEHOLIDAY: 'employeeHoliday/UPDATE_EMPLOYEEHOLIDAY',
  DELETE_EMPLOYEEHOLIDAY: 'employeeHoliday/DELETE_EMPLOYEEHOLIDAY',
  RESET: 'employeeHoliday/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeHoliday>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmployeeHolidayState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeHolidayState = initialState, action): EmployeeHolidayState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEHOLIDAY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEHOLIDAY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEEHOLIDAY):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEEHOLIDAY):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEEHOLIDAY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEHOLIDAY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEHOLIDAY):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEEHOLIDAY):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEEHOLIDAY):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEEHOLIDAY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEHOLIDAY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEHOLIDAY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEEHOLIDAY):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEEHOLIDAY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEEHOLIDAY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/v1/employee-holidays';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeHoliday> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEHOLIDAY_LIST,
    payload: axios.get<IEmployeeHoliday>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmployeeHoliday> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEHOLIDAY,
    payload: axios.get<IEmployeeHoliday>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmployeeHoliday> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEEHOLIDAY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeHoliday> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEEHOLIDAY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeHoliday> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEEHOLIDAY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
