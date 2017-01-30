package com.example.rinko.myepidroid;

/**
 * Created by Caroline on 31/01/2015.
 */
public class MySingleton {
    private static MySingleton instance;
    private String token;
    private String login;
    private String courseCode;
    private String semester;
    private String location;
    private String scolaryear = "2015";

    public String customVar;

    public static void initInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new MySingleton();
        }
    }

    public static MySingleton getInstance()
    {
        // Return the instance
        return instance;
    }

    private MySingleton()
    {
    }

    public void customSingletonMethod()
    {
    }
    void            setToken(String _token)
    {
        token = _token;
    }
    String          getToken()
    {
        return(token);
    }

    String          getScolaryear()
    {
        return(scolaryear);
    }

    void            setLogin(String _l)
    {
        login = _l;
    }
    String          getLogin()
    {
        return (login);
    }

    void            setCourseCode(String _cc)
    {
        courseCode = _cc;
    }
    String          getCourseCode()
    {
        return (courseCode);
    }

    void            setSemester(String _s)
    {
        semester = _s;
    }
    String          getSemester()
    {
        return (semester);
    }

    void            setLocation(String _l)
    {
        location = _l;
    }
    String          getLocation()
    {
        return (location);
    }
}
