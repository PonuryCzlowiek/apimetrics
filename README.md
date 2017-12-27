**TASK DEFINITION:**

Pick a technology used in web applications development. 
Figure out what's worth looking at from the application performance point of view.
Think of some performance metrics. Write a script (python would be nice) or program (java/c++) 
that collects your metrics every minute and stores them for some period of time.
Add a CSV export capability, present the results in a spread sheet or even present as a chart on a web page.
Be ready to answer questions about the implementation and your choice of metrics.

**SPECIFICATION:**

Technology used - Spring, Chart.js

**Metrics:**

FULL PROCESSING TIME:
    SpringControllerAnalysisAspect.measureControllerMethodExecutionTime

ENDPOINT (Source (mobile/web), System, Browser, Browser version, IP source):
    SpringControllerAnalysisAspect.logRequestsPerClient    

EXCEPTIONS:
    SpringControllerAnalysisAspect.logExceptions
    
CPU/MEMORY USAGE OVER TIME:
    MemoryChecker.checkMemoryState