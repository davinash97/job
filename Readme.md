# Job Microservice for [Jobsphere](https://github.com/jobsphere)

### For Job: 
#### Body, Parameter and URI everything works

> Get all Jobs `/job`

 | Method | Description                                                        |
 | :----: | :----------------------------------------------------------------- |
 | `GET`  | A **GET** request to this endpoint will return all available Jobs. |

> Job by Profile ID `{profile_id}/job`

 | Method | Description                                                                                | Parameters                                                                       |
 | :----: | :----------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------- |
 | `GET`  | A **GET** request with Profile ID to this endpoint will return all Job(s) from Profile ID. | `profile_id`, `job_id`                                                           |
 | `POST` | A **POST** request with Profile ID to this endpoint will create a new Job.                 | `profile_id`, `title`, `description`, `no_of_openings`, `organization`, `skills` |

> Job by Profile and Job ID `{profile_id}/job/{job_id}`

 |  Method  | Description                                                                                         | Parameters                                                                       |
 | :------: | :-------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------- |
 |  `GET`   | A **GET** request with Profile ID and Job ID will retrieve the specific job from specific profile.  | `profile_id`, `job_id`                                                           |
 | `DELETE` | A **DELETE** request with Profile ID and Job ID will delete the specific job from specific profile. | `profile_id`, `job_id`                                                           |
 |  `PUT`   | A **PUT** request with Profile ID and Job ID will update the specific job from specific profile.    | `profile_id`, `title`, `description`, `no_of_openings`, `organization`, `skills` |