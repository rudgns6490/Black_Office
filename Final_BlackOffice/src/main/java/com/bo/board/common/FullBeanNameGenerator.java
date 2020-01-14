package com.bo.board.common;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public class FullBeanNameGenerator implements org.springframework.beans.factory.support.BeanNameGenerator {

	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		return definition.getBeanClassName(); 
	}
}


/*보통 Controller를 만들어줄 때, 같은 이름의 Controller가 프로젝트 내에 2개 이상이 있다면 처음 Spring이 시작할 때 오류가 나곤 한다. 
  이유는 같은 이름의 Bean이 생성되기 때문에 하지만 같은 이름의 Controller를 생성할 필요가 있었기에 이것을 해결해야했다.
 generateBeanName메서드를 통해서 먼저 Controller인지 판별한 후, 
 Controller라면 다시 generateFullBeanName메서드를 통해 패키지를 포함한 전체 이름을 반환하도록 하였다. 
 이렇게 되면 위에서 예로 들었던 2개의 Controller의 Bean 이름은 아래와 같이 만들어지게 된다.
 user.HomeController admin.HomeController
이렇게 패키지명까지 포함하게 되면서 Spring 시작 시, Bean 이름을 생성할 때 오류가 나지 않고 정상적으로 생성이 되게 된다.
 */
 