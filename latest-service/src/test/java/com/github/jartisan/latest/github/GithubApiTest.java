package com.github.jartisan.latest.github;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHTag;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.jartisan.latest.configuration.GitHubConfig;
import com.github.jartisan.latest.global.entity.GithubInfo;
import com.github.jartisan.latest.global.utils.GithubUtil;
import com.github.jartisan.latest.global.utils.HttpClientUtil;

public class GithubApiTest {
	
	private static Logger log = LoggerFactory.getLogger(GithubApiTest.class);
	public static final String GITHUB_PROJECT_INFO = "https://api.github.com/repos/alibaba/fastjson";
	
	@Autowired
	private GitHubConfig gitHubConfig;
	

	@Test
	public void testGitHubProjectInfo() {
		String body =HttpClientUtil.httpGet(GITHUB_PROJECT_INFO);
		JSONObject json = JSON.parseObject(body);
		int starCount = json.getIntValue("stargazers_count");
		int watcherCount = json.getIntValue("subscribers_count");
		int forkCount = json.getIntValue("forks");
		Date created = json.getDate("created_at");
		
		log.info("starCount:{}",starCount);
		log.info("watcherCount:{}",watcherCount);  
		log.info("forkCount:{}",forkCount);  
		log.info("created:{}",DateFormatUtils.format(created, "yyyy-MM-dd"));  
		
	}
	
	@Test
	public void testGetProjectInfo() {
		GithubInfo info =GithubUtil.getProjectInfo(GITHUB_PROJECT_INFO,gitHubConfig.getToken());
		log.info("GithubInfo:{}",info);
	}
	
	@Test
	public void testGetTags() {
		GithubInfo info =GithubUtil.getTags(GITHUB_PROJECT_INFO,gitHubConfig.getToken());
		log.info("GithubInfo:{}",info);
		log.info("tags:{}",info.getTags());
	}
	@Test
	public void testGetLastTag() {
		String tag =GithubUtil.getLastTag(GITHUB_PROJECT_INFO,gitHubConfig.getToken());
		log.info("LastTag:{}",tag);
	}
	
	@Test
	public void testGetReleases() {
		GithubInfo info =GithubUtil.getReleases(GITHUB_PROJECT_INFO,gitHubConfig.getToken());
		log.info("GithubInfo:{}",info);
		log.info("tags:{}",info.getReleases());
	}
	@Test
	public void testGetLastRelease() {
		String release =GithubUtil.getLastRelease(GITHUB_PROJECT_INFO,gitHubConfig.getToken());
		log.info("LastRelease:{}",release);
	}
	
	/*
	 * for (Element link : links) { //String linkHref = link.attr("href"); String
	 * linkText = link.text(); log.info("GithubInfo:{}",linkText); }
	 */
	@Test
	public void testGithubApiGetReleases() {
		String[] repos = {"Microsoft/vscode",
				"Activiti/Activiti",
				"spring-projects/sts4",
				//"apple/swift"
				};
		for(String repo : repos) {
			String body = HttpClientUtil.httpGet("https://github.com/"+repo+"/releases");
			Document doc = Jsoup.parse(body);
			Elements divs = doc.select("div[class=commit js-details-container Details]");
			StringBuilder releases = new StringBuilder();
			releases.append(repo +" releases :");
				for (org.jsoup.nodes.Element div : divs) {
					Element link = div.getElementsByTag("a").get(0);
					String linkText = link.text();
					releases.append(linkText).append("  ");
				}
				log.info(releases.toString());	
			
			}
		
	}
	
	
	@Test
	public void testGithubApi() {
		try {
			GitHub gitHub = GitHub.connectUsingOAuth("797fc71c24b871b722095c16f5d5c998f44dbae5");
			// add the repository that have latest release
			
			List<GHTag> tags = gitHub.getRepository("Activiti/Activiti").listTags().asList();
			log.info("tags:{}",tags.get(0).getName());
			GHRelease release = gitHub.getRepository("Activiti/Activiti").getLatestRelease();
			log.info("LastRelease:{}",release.getName());
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	//curl -X PUT -H "Content-Type: application/json" https://api.github.com/repos/bookmark/bmarks/contents/dir/test?access_token=796365420b6bb92eca9d02ammm309a8032f0571e -d '{"message": "my commit message","content": "bXkgdXBkYXRlZCBmaWxlIGNvmmmlbnRz","sha": "0d5a690c8fad5e605a6e8766295d9d459d65de42"}'
	@Test // issue 230
    public void listFiles() throws Exception {
		GithubInfo info = GithubUtil.getCommits("https://api.github.com/repos/jartisan2001/greleases",gitHubConfig.getToken());
         //7a9d85331ad988fedfb36511590b516e382a450a
		log.info("getCommits:{}",info.getCommits());
		String commiturl= "https://api.github.com/repos/jartisan2001/greleases/git/commits/7a9d85331ad988fedfb36511590b516e382a450a";
		String outStr = "{\"message\": \"my commit message\",\"content\": \"bXkgdXBkYXRlZCBmaWxlIGNvmmmlbnRz\",\"sha\": \"7a9d85331ad988fedfb36511590b516e382a450a\"}";
		String result = GithubUtil.httpPut(commiturl, outStr, GithubUtil.buildTokenParams(gitHubConfig.getToken()));
		log.info("getCommits:{}",result);
    }
	
	@Test
	public void testGithubApiGetReleases4Html() {
		String[] repos = {"Microsoft/vscode",
				"Activiti/Activiti",
				"spring-projects/sts4",
				//"apple/swift"
				};
		for(String repo : repos) {
			GithubInfo releases =GithubUtil.getReleases4html(repo,gitHubConfig.getToken());
			log.info("LastRelease:{}",releases.getReleases());	
		}
				
	}

	
	
	@Test
	public void testFullNameGithubApi() throws MalformedURLException {
		URL url = new URL(GITHUB_PROJECT_INFO);
        String path = url.getPath();
        String fullName = StringUtils.remove(path, "/repos/");
        log.info("fullName:{}",fullName);	
        
				
	}
	
	
	
}
